/*
 * BunnyEmu - A Java WoW sandbox/emulator
 * https://github.com/marijnz/BunnyEmu
 */
package com.wow;

import com.wow.commons.database.pool.impl.ConnectionFactory;
import com.wow.config.Config;
import com.wow.network.Connection;
import com.wow.network.LogonConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.LogManager;

/**
 * 
 * To login: Run and login with a WoW gameserverpackets with any username but make sure to
 * use the password: "password".
 * 
 * @author Marijn
 */
public class Server
{
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private static final String LOG_FOLDER = "log";
    private static final String LOG_NAME = "./log.cfg";

	public static String realmlist = null;

	private ServerSocket serverSocket;
	private ArrayList<Connection> connections = new ArrayList<Connection>(10);

    //private final SelectorThread<GameClient> _selectorThread;
    //private final GamePacketHandler _gamePacketHandler;
    //private final DeadLockDetector _deadDetectThread;
    public static Server server;

    /*public Server() throws Exception
    {
        long serverLoadStart = System.currentTimeMillis();

        LOG.info("{}: Used memory: {}MB.", getClass().getSimpleName(), getUsedMemoryMB());

        new File("log/game").mkdirs();


        if (Config.DEADLOCK_DETECTOR)
        {
            _deadDetectThread = new DeadLockDetector();
            _deadDetectThread.setDaemon(true);
            _deadDetectThread.start();
        }
        else
        {
            _deadDetectThread = null;
        }
        System.gc();
        // maxMemory is the upper limit the jvm can use, totalMemory the size of
        // the current allocation pool, freeMemory the unused memory in the allocation pool
        long freeMem = ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) + Runtime.getRuntime().freeMemory()) / 1048576;
        long totalMem = Runtime.getRuntime().maxMemory() / 1048576;
        LOG.info("{}: Started, free memory {} Mb of {} Mb", getClass().getSimpleName(), freeMem, totalMem);
        Toolkit.getDefaultToolkit().beep();

        final SelectorConfig sc = new SelectorConfig();
        sc.MAX_READ_PER_PASS = Config.MMO_MAX_READ_PER_PASS;
        sc.MAX_SEND_PER_PASS = Config.MMO_MAX_SEND_PER_PASS;
        sc.SLEEP_TIME = Config.MMO_SELECTOR_SLEEP_TIME;
        sc.HELPER_BUFFER_COUNT = Config.MMO_HELPER_BUFFER_COUNT;
        sc.TCP_NODELAY = Config.MMO_TCP_NODELAY;

        InetAddress bindAddress = null;
        try
        {
            bindAddress = InetAddress.getByName(Config.REALMLIST);
        }
        catch (UnknownHostException e1)
        {
            LOG.error("{}: The GameServer bind address is invalid, using all avaliable IPs!", getClass().getSimpleName(), e1);
        }

        try
        {
            _selectorThread.openServerSocket(bindAddress, Config.GAMESERVER_PORT);
            _selectorThread.start();
            LOG.info("{}: is now listening on: {}:{}", getClass().getSimpleName(), Config.REALMLIST, Config.GAMESERVER_PORT);

        }
        catch (IOException e)
        {
            LOG.error("{}: Failed to open server socket!", getClass().getSimpleName(), e);
            System.exit(1);
        }

        LOG.info("{}: Maximum numbers of connected players: {}", getClass().getSimpleName(), Config.MAXIMUM_ONLINE_USERS);
        LOG.info("{}: Server loaded in {} seconds.", getClass().getSimpleName(), TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - serverLoadStart));

    }*/

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
            LOG.info("Launched BunnyEmu - listening on " + realmlist);
			
			InetAddress address = InetAddress.getByName(realmlist);
			serverSocket = new ServerSocket(Config.GAMESERVER_PORT, 0, address);

			/* load database connection */
			// TODO: Some explanation how to start up the database when there isn't one?
			//DatabaseConnection.initConnectionPool();

            LOG.info("BunnyEmu is open-source: https://github.com/marijnz/BunnyEmu");
            LOG.info("Remember to create an account before logging in.");
			
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
            LOG.warn("ERROR: port " + Config.GAMESERVER_PORT + " is not available!");
		}
		
		try
        {
			while (true)
            {
				try
                {
					LogonConnection connection = new LogonConnection(serverSocket.accept());
                    LOG.warn("Client connected to logon serverpackets.");
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
            LOG.warn("Accept failed: " + Config.GAMESERVER_PORT);
		}
	}

    public static void main(String[] args) throws Exception
    {
        /*** Main ***/
        // Create log folder
        File logFolder = new File(Config.DATAPACK_ROOT, LOG_FOLDER);
        logFolder.mkdir();

        // Create input stream for log file -- or store file data into memory
        try (InputStream is = new FileInputStream(new File(LOG_NAME)))
        {
            LogManager.getLogManager().readConfiguration(is);
        }

        // Initialize config
        printSection("Config");
        Config.load();
        printSection("Database");
        ConnectionFactory.getInstance();

        realmlist = Config.REALMLIST;

        if (realmlist.isEmpty())
        {
            LOG.warn("No realmlist set in server.properties, unable to start.");
            System.exit(0);
        }

			/* does user want a GUI */
        if (Config.ENABLE_GUI)
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ServerWindow.create();
            Thread.sleep(200);
        }

        new Server().launch();
    }

    public long getUsedMemoryMB()
    {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576;
    }

    public static void printSection(String s)
    {
        s = "=[ " + s + " ]";
        while (s.length() < 61)
        {
            s = "-" + s;
        }
        LOG.info(s);
    }
}
