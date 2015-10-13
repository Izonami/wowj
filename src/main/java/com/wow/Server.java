/*
 * BunnyEmu - A Java WoW sandbox/emulator
 * https://github.com/marijnz/BunnyEmu
 */
package com.wow;

import com.wow.config.Config;
import com.wow.commons.db.DatabaseConnection;
import com.wow.network.Connection;
import com.wow.network.LogonConnection;
import misc.Logger;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * 
 * To login: Run and login with a WoW gameserverpackets with any username but make sure to
 * use the password: "password".
 * 
 * @author Marijn
 */
public class Server {

	public static String realmlist = null;

	private ServerSocket serverSocket;
	private ArrayList<Connection> connections = new ArrayList<Connection>(10);

	public static void main(String[] args)
	{
		try
		{
			Logger.printToConsole = true;
			
			Config.load();
			
			realmlist = Config.REALMLIST;

			if (realmlist.isEmpty())
			{
				Logger.writeLog("No realmlist set in server.properties, unable to start.", Logger.LOG_TYPE_ERROR);
				System.exit(0);
			}
			
			/* does user want a GUI */
			if (Config.ENABLE_GUI)
            {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				ServerWindow.create();
				Thread.sleep(200);
			}
		}
        catch (Exception e)
        {

			e.printStackTrace();
		}

		new Server().launch();
	}

	public void launch()
    {
		//RealmHandler.addRealm(new Realm(1, "Server test 1", "31.220.24.8", 3344, 1));
		listenSocket();
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	private void listenSocket()
    {
		try
        {
			Logger.writeLog("Launched BunnyEmu - listening on " + realmlist, Logger.LOG_TYPE_VERBOSE);
			
			InetAddress address = InetAddress.getByName(realmlist);
			serverSocket = new ServerSocket(Config.GAMESERVER_PORT, 0, address);

			/* load database connection */
			// TODO: Some explanation how to start up the database when there isn't one?
			DatabaseConnection.initConnectionPool();
			
			Logger.writeLog("BunnyEmu is open-source: https://github.com/marijnz/BunnyEmu", Logger.LOG_TYPE_VERBOSE);
			Logger.writeLog("Remember to create an account before logging in.", Logger.LOG_TYPE_VERBOSE);
			
			/* console commands are handled by this thread if no GUI */
			if (!Config.ENABLE_GUI)
            {
				Runnable loggerRunnable = new ConsoleLoggerCMD();
				Thread loggerThread = new Thread(loggerRunnable);
				loggerThread.start();
			}

		}
        catch (IOException e)
        {
			Logger.writeLog("ERROR: port "+ Config.GAMESERVER_PORT + " is not available!", Logger.LOG_TYPE_WARNING);
		}
		
		try
        {
			while (true)
            {
				try
                {
					LogonConnection connection = new LogonConnection(serverSocket.accept());
					Logger.writeLog("Client connected to logon serverpackets.", Logger.LOG_TYPE_VERBOSE);
					connections.add(connection);
				}
                catch(NullPointerException e)
                {
					continue;
				}
			}
		}
        catch (IOException e)
        {
			Logger.writeLog("Accept failed: " + Config.GAMESERVER_PORT, Logger.LOG_TYPE_WARNING);
		}
	}
}
