package org.wowj.commons.database.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wowj.commons.database.DatabaseConfig;
import org.wowj.commons.database.IConnectionFactory;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public class ConnectionFactory
{
    public static IConnectionFactory getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder
    {
        private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);

        protected static final IConnectionFactory INSTANCE;

        static
        {
            switch (DatabaseConfig.DATABASE_CONNECTION_POOL)
            {
                default:
                case "HikariCP":
                {
                    INSTANCE = new HikariCPConnectionFactory();
                    break;
                }
                case "C3P0":
                {
                    INSTANCE = new C3P0ConnectionFactory();
                    break;
                }
                case "BoneCP":
                {
                    INSTANCE = new BoneCPConnectionFactory();
                    break;
                }
            }
            LOG.info("Using {} connection pool.", INSTANCE.getClass().getSimpleName().replace("ConnectionFactory", ""));
        }
    }
}
