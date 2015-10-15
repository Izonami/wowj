package org.wowj.commons.database;

import org.wowj.commons.configuration.ConfigEngine;
import org.wowj.commons.configuration.ConfigField;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class DatabaseConfig extends ConfigEngine
{
    public static final String CONFIG_FILE = "conf/database/database.properties";

    @ConfigField(config = "server", fieldName = "Driver", value = "com.mysql.jdbc.Driver")
    public static String WORLD_DATABASE_DRIVER;
    @ConfigField(config = "server", fieldName = "URL", value = "jdbc:mysql://localhost:3306/")
    public static String WORLD_DATABASE_URL;
    @ConfigField(config = "server", fieldName = "Login", value = "test")
    public static String WORLD_DATABASE_USER;
    @ConfigField(config = "server", fieldName = "Password", value = "")
    public static String WORLD_DATABASE_PASSWORD;
    @ConfigField(config = "server", fieldName = "dbWorld", value = "world")
    public static String WORLD_DATABASE_NAME;
    @ConfigField(config = "server", fieldName = "MaximumDbConnections", value = "10")
    public static int WORLD_DATABASE_CONNECTIONS_MAX ;
    @ConfigField(config = "server", fieldName = "MaximumDbIdleTime", value = "0")
    public static int DATABASE_MAX_IDLE_TIME ;
    @ConfigField(config = "server", fieldName = "ConnectionPool", value = "C3P0")
    public static String DATABASE_CONNECTION_POOL ;

}
