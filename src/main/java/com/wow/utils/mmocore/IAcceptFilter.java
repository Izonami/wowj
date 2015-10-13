package com.wow.utils.mmocore;

import java.nio.channels.SocketChannel;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public interface IAcceptFilter
{
    public boolean accept(SocketChannel sc);
}
