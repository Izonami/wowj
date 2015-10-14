package com.wow.loginserver;

import com.wow.commons.database.pool.impl.ConnectionFactory;
import com.wow.loginserver.model.data.AccountInfo;
import com.wow.loginserver.network.LoginClient;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class LoginController
{
    protected final Logger LOG = Logger.getLogger(LoginController.class.getName());

    private static LoginController _instance;

    /** Time before kicking the client if he didn't logged yet */
    public static final int LOGIN_TIMEOUT = 60 * 1000;

    /** Authed Clients on LoginServer */
    protected Map<String, LoginClient> _loginServerClients = new ConcurrentHashMap<>();

    private final Map<InetAddress, Integer> _failedLoginAttemps = new HashMap<>();
    private final Map<InetAddress, Long> _bannedIps = new ConcurrentHashMap<>();

    // SQL Queries
    private static final String AUTOCREATE_ACCOUNTS_INSERT = "INSERT INTO accounts (login, password, lastactive, lastIP) values (?, ?, ?, ?)";
    private static final String ACCOUNT_INFO_UPDATE = "UPDATE accounts SET lastactive = ?, lastIP = ? WHERE login = ?";
    private static final String USER_INFO_SELECT = "SELECT login, password FROM accounts WHERE login=?";


    private LoginController() throws GeneralSecurityException
    {
        LOG.info("Loading LoginController");

        Thread purge = new PurgeThread();
        purge.setDaemon(true);
        purge.start();
    }

    public static void load() throws GeneralSecurityException
    {
        synchronized (LoginController.class)
        {
            if (_instance == null)
            {
                _instance = new LoginController();
            }
            else
            {
                throw new IllegalStateException("LoginController can only be loaded a single time.");
            }
        }
    }

    public static LoginController getInstance()
    {
        return _instance;
    }

    public AccountInfo retriveAccountInfo(InetAddress clientAddr, String login, String password)
    {
        return retriveAccountInfo(clientAddr, login, password, true);
    }

    private void recordFailedLoginAttemp(InetAddress addr)
    {
        // We need to synchronize this!
        // When multiple connections from the same address fail to login at the
        // same time, unexpected behavior can happen.
        Integer failedLoginAttemps;
        synchronized (_failedLoginAttemps)
        {
            failedLoginAttemps = _failedLoginAttemps.get(addr);
            if (failedLoginAttemps == null)
            {
                failedLoginAttemps = 1;
            }
            else
            {
                ++failedLoginAttemps;
            }

            _failedLoginAttemps.put(addr, failedLoginAttemps);
        }

        /*if (failedLoginAttemps >= Config.LOGIN_TRY_BEFORE_BAN)
        {
            addBanForAddress(addr, Config.LOGIN_BLOCK_AFTER_BAN * 1000);
            // we need to clear the failed login attempts here, so after the ip ban is over the client has another 5 attempts
            clearFailedLoginAttemps(addr);
            _log.warning("Added banned address " + addr.getHostAddress() + "! Too many login attemps.");
        }*/
    }

    private void clearFailedLoginAttemps(InetAddress addr)
    {
        synchronized (_failedLoginAttemps)
        {
            _failedLoginAttemps.remove(addr);
        }
    }

    private AccountInfo retriveAccountInfo(InetAddress addr, String login, String password, boolean autoCreateIfEnabled)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] raw = password.getBytes(StandardCharsets.UTF_8);
            String hashBase64 = Base64.getEncoder().encodeToString(md.digest(raw));

            try (Connection con = ConnectionFactory.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(USER_INFO_SELECT))
            {
                ps.setString(1, login);
                try (ResultSet rset = ps.executeQuery())
                {
                    if (rset.next())
                    {
                        if (true) //TODO: Add Debug config
                        {
                            LOG.fine("Account '" + login + "' exists.");
                        }

                        AccountInfo info = new AccountInfo(rset.getString("login"), rset.getString("password"));
                        if (!info.checkPassHash(hashBase64))
                        {
                            // wrong password
                            recordFailedLoginAttemp(addr);
                            return null;
                        }

                        clearFailedLoginAttemps(addr);
                        return info;
                    }
                }
            }

            /*if (!autoCreateIfEnabled || !Config.AUTO_CREATE_ACCOUNTS)
            {
                // account does not exist and auto create account is not desired
                recordFailedLoginAttemp(addr);
                return null;
            }*/

            try (Connection con = ConnectionFactory.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(AUTOCREATE_ACCOUNTS_INSERT))
            {
                ps.setString(1, login);
                ps.setString(2, hashBase64);
                ps.setLong(3, System.currentTimeMillis());
                ps.setString(4, addr.getHostAddress());
                ps.execute();
            }
            catch (Exception e)
            {
                LOG.log(Level.WARNING, "Exception while auto creating account for '" + login + "'!", e);
                return null;
            }

            LOG.info("Auto created account '" + login + "'.");
            return retriveAccountInfo(addr, login, password, false);
        }
        catch (Exception e)
        {
            LOG.log(Level.WARNING, "Exception while retriving account info for '" + login + "'!", e);
            return null;
        }
    }

    class PurgeThread extends Thread
    {
        public PurgeThread()
        {
            setName("PurgeThread");
        }

        @Override
        public void run()
        {
            while (!isInterrupted())
            {
                for (LoginClient client : _loginServerClients.values())
                {
                    if (client == null)
                    {
                        continue;
                    }
                    if ((client.getConnectionStartTime() + LOGIN_TIMEOUT) < System.currentTimeMillis())
                    {
                        LOG.info("client.close(LoginFailReason.REASON_ACCESS_FAILED);");
                    }
                }

                try
                {
                    Thread.sleep(LOGIN_TIMEOUT / 2);
                }
                catch (InterruptedException e)
                {
                    return;
                }
            }
        }
    }

    public static enum AuthLoginResult
    {
        INVALID_PASSWORD,
        ACCOUNT_BANNED,
        ALREADY_ON_LS,
        ALREADY_ON_GS,
        AUTH_SUCCESS
    }
}
