package org.wowj.commons.database;

import org.wowj.commons.configuration.ConfigEngine;
import org.wowj.commons.configuration.ConfigField;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class DatabaseConfig extends ConfigEngine
{
    public static final String CONFIG_FILE = "conf/database/database.properties";

    /**
     *      Config for All base
     */
    @ConfigField(config = "database", fieldName = "ConnectionPool", value = "C3P0")
    public static String DATABASE_CONNECTION_POOL ;


    /**
     *      Config for World base
     */
    @ConfigField(config = "database", fieldName = "WorldDriver", value = "com.mysql.jdbc.Driver")
    public static String WORLD_DATABASE_DRIVER;
    @ConfigField(config = "database", fieldName = "WorldURL", value = "jdbc:mysql://localhost:3306/world")
    public static String WORLD_DATABASE_URL;
    @ConfigField(config = "database", fieldName = "WorldLogin", value = "test")
    public static String WORLD_DATABASE_USER;
    @ConfigField(config = "database", fieldName = "WorldPassword", value = "test")
    public static String WORLD_DATABASE_PASSWORD;
    @ConfigField(config = "database", fieldName = "WorldMaximumDbConnections", value = "10")
    public static int WORLD_DATABASE_CONNECTIONS_MAX ;
    @ConfigField(config = "database", fieldName = "WorldMaximumDbIdleTime", value = "0")
    public static int WORLD_DATABASE_MAX_IDLE_TIME ;



    /**
     *      Config for Character base
     */
    @ConfigField(config = "database", fieldName = "CharsDriver", value = "com.mysql.jdbc.Driver")
    public static String CHARS_DATABASE_DRIVER;
    @ConfigField(config = "database", fieldName = "CharsURL", value = "jdbc:mysql://localhost:3306/characters")
    public static String CHARS_DATABASE_URL;
    @ConfigField(config = "database", fieldName = "CharsLogin", value = "test")
    public static String CHARS_DATABASE_USER;
    @ConfigField(config = "database", fieldName = "CharsPassword", value = "test")
    public static String CHARS_DATABASE_PASSWORD;
    @ConfigField(config = "database", fieldName = "CharsMaximumDbConnections", value = "10")
    public static int CHARS_DATABASE_CONNECTIONS_MAX ;
    @ConfigField(config = "database", fieldName = "CharsMaximumDbIdleTime", value = "0")
    public static int CHARS_DATABASE_MAX_IDLE_TIME ;


    /**
     *      Config for Account base
     */
    @ConfigField(config = "database", fieldName = "AccountDriver", value = "com.mysql.jdbc.Driver")
    public static String ACCOUNT_DATABASE_DRIVER;
    @ConfigField(config = "database", fieldName = "AccountURL", value = "jdbc:mysql://localhost:3306/account")
    public static String ACCOUNT_DATABASE_URL;
    @ConfigField(config = "database", fieldName = "AccountLogin", value = "test")
    public static String ACCOUNT_DATABASE_USER;
    @ConfigField(config = "database", fieldName = "AccountPassword", value = "test")
    public static String ACCOUNT_DATABASE_PASSWORD;
    @ConfigField(config = "database", fieldName = "AccountMaximumDbConnections", value = "10")
    public static int ACCOUNT_DATABASE_CONNECTIONS_MAX ;
    @ConfigField(config = "database", fieldName = "AccountMaximumDbIdleTime", value = "0")
    public static int ACCOUNT_DATABASE_MAX_IDLE_TIME ;


    //Load config
    public DatabaseConfig()
    {
        loadConfig(DatabaseConfig.class, "database", CONFIG_FILE);
    }
}
