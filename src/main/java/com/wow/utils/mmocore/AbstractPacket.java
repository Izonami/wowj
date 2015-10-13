package com.wow.utils.mmocore;

import java.nio.ByteBuffer;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public abstract class AbstractPacket<T extends MMOClient>
{
    protected ByteBuffer _buf;

    protected T _client;

    public final T getClient()
    {
        return _client;
    }
}
