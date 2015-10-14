package com.wow.loginserver.network.clientpackets;

import com.wow.loginserver.network.LoginClient;
import com.wow.utils.mmocore.ReceivablePacket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public abstract class LoginClientPacket extends ReceivablePacket<LoginClient>
{
    private static Logger LOG = Logger.getLogger(LoginClientPacket.class.getName());

    @Override
    protected final boolean read()
    {
        try
        {
            return readImpl();
        }
        catch (Exception e)
        {
            LOG.log(Level.SEVERE, "ERROR READING: " + this.getClass().getSimpleName() + ": " + e.getMessage(), e);
            return false;
        }
    }

    protected abstract boolean readImpl();
}
