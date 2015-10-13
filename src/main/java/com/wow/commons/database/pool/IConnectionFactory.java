package com.wow.commons.database.pool;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public interface IConnectionFactory
{
    DataSource getDataSource();

    Connection getConnection();

    void close();
}
