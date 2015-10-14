package com.wow.loginserver.network;

import com.wow.utils.mmocore.IPacketHandler;
import com.wow.utils.mmocore.ReceivablePacket;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class LoginPacketHandler implements IPacketHandler<LoginClient>
{
    protected static final Logger LOG = Logger.getLogger(LoginPacketHandler.class.getName());

    @Override
    public ReceivablePacket<LoginClient> handlePacket(ByteBuffer buf, LoginClient client) {
        return null;
    }
}
