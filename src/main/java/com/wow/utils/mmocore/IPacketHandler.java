package com.wow.utils.mmocore;

import java.nio.ByteBuffer;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public interface IPacketHandler<T extends MMOClient<?>>
{
    public ReceivablePacket<T> handlePacket(ByteBuffer buf, T client);
}