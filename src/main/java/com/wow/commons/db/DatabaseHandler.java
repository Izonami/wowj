package com.wow.commons.db;

import com.wow.commons.database.pool.impl.ConnectionFactory;
import com.wow.config.Config;
import misc.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHandler {
	
	private static String authDB = "USE " + Config.DATABASE_AUTH;

	/* creates an account in the authDB */
	public static boolean createAccount(String userName, String hashPW) {
		try(Connection con = ConnectionFactory.getInstance().getConnection();
            Statement st = con.createStatement())
		{
			/* try to add an account here from userName and password hash */
			st.execute(authDB);
			st.executeUpdate("INSERT INTO `account` (`username`, `hashPW`) VALUES (" + "'" + userName + "', '" + hashPW + "');");
			
			Logger.writeLog("INSERT INTO `account` (`username`, `hashPW`) VALUES (" + "'" + userName + "', '" + hashPW + "');", Logger.LOG_TYPE_VERBOSE);

			return true;
		}
		// this will throw duplicate errors because of Unique constraint
		// just silence the error and red text and tell gameserverpackets creation failed
        catch (Exception e)
        {
            Logger.writeLog("Failed loading INSERT INTO `account`:", Logger.LOG_TYPE_ERROR);
            return false;
        }
	}

	/* this needs to be modified to return the exact issue */
	public static String[] queryAuth(String username) {
		try (Connection con = ConnectionFactory.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rset = st.executeQuery("SELECT * FROM account WHERE username = '" + username + "'"))
        {
			int ID = -1;
			String[] userInfo = new String[2];

			/* ID, Username, Password (Hashed) */
			if (rset.next()) {
				ID = rset.getInt("id");
				userInfo[0] = String.valueOf(ID);
				userInfo[1] = rset.getString("hashPW");
			}
			else
            {
				userInfo = null;
			}

			return userInfo;
		} 
		catch (Exception e)
        {
			e.printStackTrace();
			return null;
		}
	}
}