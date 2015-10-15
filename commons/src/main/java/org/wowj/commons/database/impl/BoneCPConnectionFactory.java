package org.wowj.commons.database.impl;

import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wowj.commons.database.AbstractConnectionFactory;
import org.wowj.commons.database.DatabaseConfig;
import org.wowj.commons.database.IConnectionFactory;

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
        _dataSource.setJdbcUrl(DatabaseConfig.WORLD_DATABASE_URL);
        _dataSource.setUsername(DatabaseConfig.WORLD_DATABASE_USER);
        _dataSource.setPassword(DatabaseConfig.WORLD_DATABASE_PASSWORD);
        _dataSource.setPartitionCount(PARTITION_COUNT);
        _dataSource.setMaxConnectionsPerPartition(DatabaseConfig.WORLD_DATABASE_CONNECTIONS_MAX);
        _dataSource.setIdleConnectionTestPeriod(DatabaseConfig.DATABASE_MAX_IDLE_TIME, TimeUnit.SECONDS);
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
            LOG.warn("There has been a problem closing the data source!", e);
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
