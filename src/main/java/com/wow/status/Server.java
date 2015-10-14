package com.wow.status;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class Server
{
    private static final  int MODE_NONE = 0;
    public static final int MODE_GAMESERVER = 1;
    public static final int MODE_LOGINSERVER = 2;

    public static int serverMode = MODE_NONE;
}
