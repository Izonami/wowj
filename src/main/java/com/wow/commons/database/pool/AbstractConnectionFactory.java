package com.wow.commons.database.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public abstract class AbstractConnectionFactory implements IConnectionFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractConnectionFactory.class);

    @Override
    public Connection getConnection()
    {
        Connection con = null;
        while (con == null)
        {
            try
            {
                con = getDataSource().getConnection();
            }
            catch (SQLException e)
            {
                LOG.warn("{}: Unable to get a connection!", getClass().getSimpleName(), e);
            }
        }
        return con;
    }
}
