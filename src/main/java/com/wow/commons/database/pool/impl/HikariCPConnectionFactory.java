package com.wow.commons.database.pool.impl;

import com.wow.commons.database.pool.AbstractConnectionFactory;
import com.wow.commons.database.pool.IConnectionFactory;
import com.wow.config.Config;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
final class HikariCPConnectionFactory extends AbstractConnectionFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(HikariCPConnectionFactory.class);

    private final HikariDataSource _dataSource;

    public HikariCPConnectionFactory()
    {
        _dataSource = new HikariDataSource();
        _dataSource.setJdbcUrl(Config.DATABASE_URL);
        _dataSource.setUsername(Config.DATABASE_LOGIN);
        _dataSource.setPassword(Config.DATABASE_PASSWORD);
        _dataSource.setMaximumPoolSize(Config.DATABASE_MAX_CONNECTIONS);
        _dataSource.setIdleTimeout(Config.DATABASE_MAX_IDLE_TIME);
    }

    @Override
    public void close()
    {
        try
        {
            _dataSource.close();
        }
        catch (Exception e)
        {
            LOG.warn("There has been a problem closing the entity source!", e);
        }
    }

    @Override
    public DataSource getDataSource()
    {
        return _dataSource;
    }

    public static IConnectionFactory getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder
    {
        protected static final IConnectionFactory INSTANCE = new HikariCPConnectionFactory();
    }
}
