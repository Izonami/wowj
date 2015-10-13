package com.wow.utils.mmocore;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public interface IMMOExecutor<T extends MMOClient<?>>
{
    public void execute(ReceivablePacket<T> packet);
}
