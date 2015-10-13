package com.wow.config;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public class Config extends ConfigEngine
{
    public static final String CHARACTER_CONFIG_FILE = "dist/game/config/Character.properties";
    public static final String LOGIN_CONFIGURATION_FILE = "dist/game/config/LoginServer.properties";
    public static final String CONFIGURATION_FILE = "config/";

    @ConfigField(config = "server", fieldName = "Driver", value = "com.mysql.jdbc.Driver")
    public static String DATABASE_DRIVER;
    @ConfigField(config = "server", fieldName = "URL", value = "jdbc:mysql://localhost")
    public static String DATABASE_URL;
    @ConfigField(config = "server", fieldName = "dbPort", value = "3306")
    public static String DATABASE_PORT;
    @ConfigField(config = "server", fieldName = "dbAuth", value = "auth")
    public static String DATABASE_AUTH;
    @ConfigField(config = "server", fieldName = "dbChar", value = "char")
    public static String DATABASE_CHAR;
    @ConfigField(config = "server", fieldName = "dbWorld", value = "world")
    public static String DATABASE_WORLD;
    @ConfigField(config = "server", fieldName = "MaximumDbConnections", value = "10")
    public static int DATABASE_MAX_CONNECTIONS ;
    @ConfigField(config = "server", fieldName = "MaximumDbIdleTime", value = "0")
    public static int DATABASE_MAX_IDLE_TIME ;
    @ConfigField(config = "server", fieldName = "ConnectionPool", value = "C3P0")
    public static String DATABASE_CONNECTION_POOL ;
    @ConfigField(config = "server", fieldName = "Login", value = "test")
    public static String DATABASE_LOGIN;
    @ConfigField(config = "server", fieldName = "Password", value = "")
    public static String DATABASE_PASSWORD;

    @ConfigField(config = "server", fieldName = "realmlist", value = "test")
    public static String REALMLIST;
    @ConfigField(config = "server", fieldName = "enableGUI", value = "true")
    public static boolean ENABLE_GUI;
    @ConfigField(config = "server", fieldName = "gameserverPort", value = "7777")
    public static int GAMESERVER_PORT;


    public static void load()
    {
        loadConfig(Config.class, "server", CONFIGURATION_FILE);
    }
}
