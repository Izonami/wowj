package com.wow.utils.mmocore;

import java.nio.BufferOverflowException;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public class NioNetStringBuffer
{
    private final char[] _buf;

    private final int _size;

    private int _len;

    public NioNetStringBuffer(final int size)
    {
        _buf = new char[size];
        _size = size;
        _len = 0;
    }

    public final void clear()
    {
        _len = 0;
    }

    public final void append(final char c)
    {
        if (_len < _size)
        {
            _buf[_len++] = c;
        }
        else
        {
            throw new BufferOverflowException();
        }
    }

    @Override
    public final String toString()
    {
        return new String(_buf, 0, _len);
    }
}
