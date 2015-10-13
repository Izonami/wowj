package com.wow.utils.mmocore;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public interface IClientFactory<T extends MMOClient<?>>
{
    public T create(final MMOConnection<T> con);
}
