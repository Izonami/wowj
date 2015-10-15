package org.wowj.auth.module;

import org.wowj.commons.database.impl.ConnectionFactory;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by kuksin-mv on 15.10.2015.
 */
public class AuthModule
{
    public static boolean createAccount(String userName, String hashPW) {
        try(Connection con = ConnectionFactory.getInstance().getConnection();
            Statement st = con.createStatement())
        {
            st.executeUpdate("INSERT INTO `account` (`username`, `hashPW`) VALUES (" + "'" + userName + "', '" + hashPW + "');");
            return true;
        }
        // this will throw duplicate errors because of Unique constraint
        // just silence the error and red text and tell gameserverpackets creation failed
        catch (Exception e)
        {
            return false;
        }
    }
}
