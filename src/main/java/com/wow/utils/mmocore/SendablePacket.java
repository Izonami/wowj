package com.wow.utils.mmocore;


/**
 * Created by kuksin-mv on 13.10.2015.
 */
public abstract class SendablePacket<T extends MMOClient<?>> extends AbstractPacket<T>
{
    protected final void putInt(final int value)
    {
        _buf.putInt(value);
    }

    protected final void putDouble(final double value)
    {
        _buf.putDouble(value);
    }

    protected final void putFloat(final float value)
    {
        _buf.putFloat(value);
    }

    /**
     * Write c.
     *
     * @param value
     *        the value
     */
    protected final void writeC(final boolean value)
    {
        _buf.put((byte) (value ? 1 : 0));
    }

    /**
     * Write c.
     *
     * @param value
     *        the value (uint8)
     */
    protected final void writeC(final int value)
    {
        _buf.put((byte) value);
    }

    /**
     * Write c.
     *
     * @param value
     *        the value (uint8)
     */
    protected final void writeC(final UpdateField value)
    {
        writeC(value.getValue());
    }

    /**
     * Write <B>double</B> to the buffer. <BR>
     * 64bit double precision float (00 00 00 00 00 00 00 00)
     * @param value
     */
    protected final void writeF(final double value)
    {
        _buf.putDouble(value);
    }

    /**
     * Write <B>short</B> to the buffer. <BR>
     * 16bit integer (00 00)
     * @param value
     */
    protected final void writeH(final int value)
    {
        _buf.putShort((short) value);
    }

    /**
     * Write <B>int</B> to the buffer. <BR>
     * 32bit integer (00 00 00 00)
     * @param value
     */
    protected final void writeD(final int value)
    {
        _buf.putInt(value);
    }

    /**
     * Write <B>long</B> to the buffer. <BR>
     * 64bit integer (00 00 00 00 00 00 00 00)
     * @param value
     */
    protected final void writeQ(final long value)
    {
        _buf.putLong(value);
    }

    /**
     * Write <B>byte[]</B> to the buffer. <BR>
     * 8bit integer array (00 ...)
     * @param data
     */
    protected final void writeB(final byte[] data)
    {
        _buf.put(data);
    }

    /**
     * Write <B>String</B> to the buffer.
     * @param text
     */
    protected final void writeS(final String text)
    {
        if (text != null)
        {
            final int len = text.length();
            for (int i = 0; i < len; i++)
            {
                _buf.putChar(text.charAt(i));
            }
        }

        _buf.putChar('\000');
    }

    protected abstract void write();
}
