package com.wow.loginserver.network;

import com.wow.loginserver.LoginController;
import com.wow.loginserver.network.serverpackets.LoginServerPacket;
import com.wow.utils.mmocore.MMOClient;
import com.wow.utils.mmocore.MMOConnection;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public final class LoginClient extends MMOClient<MMOConnection<LoginClient>>
{
    private static final Logger LOG = Logger.getLogger(LoginClient.class.getName());

    public static enum LoginClientState
    {
        CONNECTED,
        AUTHED_LOGIN
    }

    private LoginClientState _state;

    private String _account;
    private Map<Integer, Integer> _charsOnServers;

    private final long _connectionStartTime;

    public LoginClient(MMOConnection<LoginClient> con)
    {
        super(con);
        _state = LoginClientState.CONNECTED;
        _connectionStartTime = System.currentTimeMillis();
    }

    @Override
    public boolean decrypt(ByteBuffer buf, int size) {
        return false;
    }

    @Override
    public boolean encrypt(ByteBuffer buf, int size) {
        return false;
    }

    public LoginClientState getState()
    {
        return _state;
    }

    public void setState(LoginClientState state)
    {
        _state = state;
    }

    public String getAccount()
    {
        return _account;
    }

    public void setAccount(String account)
    {
        _account = account;
    }

    public long getConnectionStartTime()
    {
        return _connectionStartTime;
    }

    public void sendPacket(LoginServerPacket lsp)
    {
        getConnection().sendPacket(lsp);
    }

    public void setCharsOnServ(int servId, int chars)
    {
        if (_charsOnServers == null)
        {
            _charsOnServers = new HashMap<>();
        }
        _charsOnServers.put(servId, chars);
    }

    public Map<Integer, Integer> getCharsOnServ()
    {
        return _charsOnServers;
    }

    @Override
    public void onDisconnection()
    {
        if (true)
        {
            LOG.info("DISCONNECTED: " + toString());
        }

        if ((getConnectionStartTime() + LoginController.LOGIN_TIMEOUT) < System.currentTimeMillis())
        {

        }
    }

    @Override
    public String toString()
    {
        InetAddress address = getConnection().getInetAddress();
        if (getState() == LoginClientState.AUTHED_LOGIN)
        {
            return "[" + getAccount() + " (" + (address == null ? "disconnected" : address.getHostAddress()) + ")]";
        }
        return "[" + (address == null ? "disconnected" : address.getHostAddress()) + "]";
    }

    @Override
    protected void onForcedDisconnection()
    {
        // Empty
    }
}
