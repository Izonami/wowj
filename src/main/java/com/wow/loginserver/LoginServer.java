package com.wow.loginserver;

import com.wow.commons.database.pool.impl.ConnectionFactory;
import com.wow.config.Config;
import com.wow.loginserver.network.LoginClient;
import com.wow.status.Server;
import com.wow.utils.mmocore.SelectorConfig;
import com.wow.utils.mmocore.SelectorThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public final class LoginServer
{
    private final Logger LOG = Logger.getLogger(LoginServer.class.getName());

    private static LoginServer _instance;
    private Thread _restartLoginServer;
    private SelectorThread<LoginClient> _selectorThread;

    public static void main(String[] args)
    {
        new LoginServer();
    }

    public static LoginServer getInstance()
    {
        return _instance;
    }

    private LoginServer()
    {
        _instance = this;
        Server.serverMode = Server.MODE_LOGINSERVER;

        // Local Constants
        final String LOG_FOLDER = "log"; // Name of folder for log file
        final String LOG_NAME = "./log.cfg"; // Name of log file

        /*** Main ***/
        // Create log folder
        File logFolder = new File(Config.DATAPACK_ROOT, LOG_FOLDER);
        logFolder.mkdir();

        try(InputStream is = new FileInputStream(new File(LOG_NAME)))
        {
            LogManager.getLogManager().readConfiguration();
        }
        catch (IOException e)
        {
            LOG.warning(getClass().getSimpleName() + ": " + e.getMessage());
        }

        // Load Config
        Config.load();

        // Prepare Database
        ConnectionFactory.getInstance();

        try
        {
            LoginController.load();
        }
        catch (GeneralSecurityException e)
        {
            LOG.log(Level.SEVERE, "FATAL: Failed initializing LoginController. Reason: " + e.getMessage(), e);
            System.exit(1);
        }

        InetAddress bindAddress = null;

        try
        {
            bindAddress = InetAddress.getByName(Config.REALMLIST);
        }
        catch (UnknownHostException e)
        {
            LOG.log(Level.WARNING, "WARNING: The LoginServer bind address is invalid, using all avaliable IPs. Reason: " + e.getMessage(), e);
        }

        final SelectorConfig sc = new SelectorConfig();
        sc.MAX_READ_PER_PASS = Config.MMO_MAX_READ_PER_PASS;
        sc.MAX_SEND_PER_PASS = Config.MMO_MAX_SEND_PER_PASS;
        sc.SLEEP_TIME = Config.MMO_SELECTOR_SLEEP_TIME;
        sc.HELPER_BUFFER_COUNT = Config.MMO_HELPER_BUFFER_COUNT;

        try
        {
            _selectorThread.openServerSocket(bindAddress, Config.GAMESERVER_PORT);
            _selectorThread.start();
            LOG.log(Level.INFO, getClass().getSimpleName() + ": is now listening on: " + Config.REALMLIST + ":" + Config.GAMESERVER_PORT);
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, "FATAL: Failed to open server socket. Reason: " + e.getMessage(), e);
            System.exit(1);
        }
    }

    class LoginServerRestart extends Thread
    {
        public LoginServerRestart()
        {
            setName("LoginServerRestart");
        }

        @Override
        public void run()
        {
            while (!isInterrupted())
            {
                try
                {
                    Thread.sleep(24 * 3600000);
                }
                catch (InterruptedException e)
                {
                    return;
                }
                shutdown(true);
            }
        }
    }

    public void shutdown(boolean restart)
    {
        Runtime.getRuntime().exit(restart ? 2 : 0);
    }
}
