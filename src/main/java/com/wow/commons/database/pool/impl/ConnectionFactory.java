package com.wow.commons.database.pool.impl;

import com.wow.commons.database.pool.IConnectionFactory;
import com.wow.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            switch (Config.DATABASE_CONNECTION_POOL)
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
