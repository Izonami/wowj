package com.wow.handlers;

import misc.Logger;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Manages assets/serverpackets.conf
 * 
 * @author Wazy
 *
 */
public class ConfigHandler {

	/* load properties and return them */
	public static Properties loadProperties() {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("assets/serverpackets.conf"));
			return prop;
		}
		catch (Exception e) {
			Logger.writeLog("Unable to load configuration file 'serverpackets.conf' from assets folder... terminating.", Logger.LOG_TYPE_VERBOSE);
			System.exit(0);
		}

		return null;
	}
}
