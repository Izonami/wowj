package com.wow.commons.database.pool.impl;

import com.jolbox.bonecp.BoneCPDataSource;
import com.wow.commons.database.pool.AbstractConnectionFactory;
import com.wow.commons.database.pool.IConnectionFactory;
import com.wow.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
final class BoneCPConnectionFactory extends AbstractConnectionFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(BoneCPConnectionFactory.class);

    private static final int PARTITION_COUNT = 5;

    private final BoneCPDataSource _dataSource;

    public BoneCPConnectionFactory()
    {
        _dataSource = new BoneCPDataSource();
        _dataSource.setJdbcUrl(Config.DATABASE_URL);
        _dataSource.setUsername(Config.DATABASE_LOGIN);
        _dataSource.setPassword(Config.DATABASE_PASSWORD);
        _dataSource.setPartitionCount(PARTITION_COUNT);
        _dataSource.setMaxConnectionsPerPartition(Config.DATABASE_MAX_CONNECTIONS);
        _dataSource.setIdleConnectionTestPeriod(Config.DATABASE_MAX_IDLE_TIME, TimeUnit.SECONDS);
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
        protected static final IConnectionFactory INSTANCE = new BoneCPConnectionFactory();
    }
}
