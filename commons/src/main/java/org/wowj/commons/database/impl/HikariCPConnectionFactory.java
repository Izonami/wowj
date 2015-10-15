package org.wowj.commons.database.impl;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wowj.commons.database.AbstractConnectionFactory;
import org.wowj.commons.database.DatabaseConfig;
import org.wowj.commons.database.IConnectionFactory;

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
        _dataSource.setJdbcUrl(DatabaseConfig.WORLD_DATABASE_URL);
        _dataSource.setUsername(DatabaseConfig.WORLD_DATABASE_USER);
        _dataSource.setPassword(DatabaseConfig.WORLD_DATABASE_PASSWORD);
        _dataSource.setMaximumPoolSize(DatabaseConfig.WORLD_DATABASE_CONNECTIONS_MAX);
        _dataSource.setIdleTimeout(DatabaseConfig.WORLD_DATABASE_MAX_IDLE_TIME);
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
