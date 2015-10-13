package com.wow.utils;

import com.wow.config.Config;

import java.lang.management.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 13.10.2015.
 */
public class DeadLockDetector extends Thread
{
    private static Logger LOG = Logger.getLogger(DeadLockDetector.class.getName());

    /** Interval to check for deadlocked threads */
    private static final int _sleepTime = Config.DEADLOCK_CHECK_INTERVAL * 1000;

    private final ThreadMXBean tmx;

    public DeadLockDetector()
    {
        super("DeadLockDetector");
        tmx = ManagementFactory.getThreadMXBean();
    }

    @Override
    public final void run()
    {
        boolean deadlock = false;
        while (!deadlock)
        {
            try
            {
                long[] ids = tmx.findDeadlockedThreads();

                if (ids != null)
                {
                    deadlock = true;
                    ThreadInfo[] tis = tmx.getThreadInfo(ids, true, true);
                    StringBuilder info = new StringBuilder();
                    info.append("DeadLock Found!");
                    info.append(Config.EOL);
                    for (ThreadInfo ti : tis)
                    {
                        info.append(ti.toString());
                    }

                    for (ThreadInfo ti : tis)
                    {
                        LockInfo[] locks = ti.getLockedSynchronizers();
                        MonitorInfo[] monitors = ti.getLockedMonitors();
                        if ((locks.length == 0) && (monitors.length == 0))
                        {
                            continue;
                        }

                        ThreadInfo dl = ti;
                        info.append("Java-level deadlock:");
                        info.append(Config.EOL);
                        info.append('\t');
                        info.append(dl.getThreadName());
                        info.append(" is waiting to lock ");
                        info.append(dl.getLockInfo().toString());
                        info.append(" which is held by ");
                        info.append(dl.getLockOwnerName());
                        info.append(Config.EOL);
                        while ((dl = tmx.getThreadInfo(new long[]
                                {
                                        dl.getLockOwnerId()
                                }, true, true)[0]).getThreadId() != ti.getThreadId())
                        {
                            info.append('\t');
                            info.append(dl.getThreadName());
                            info.append(" is waiting to lock ");
                            info.append(dl.getLockInfo().toString());
                            info.append(" which is held by ");
                            info.append(dl.getLockOwnerName());
                            info.append(Config.EOL);
                        }
                    }
                    LOG.warning(info.toString());

                    if (Config.RESTART_ON_DEADLOCK)
                    {
                        LOG.warning("Server has stability issues - restarting now.");
                        //Shutdown.getInstance().startTelnetShutdown("DeadLockDetector - Auto Restart", 60, true);
                    }

                }
                Thread.sleep(_sleepTime);
            }
            catch (Exception e)
            {
                LOG.log(Level.WARNING, "DeadLockDetector: ", e);
            }
        }
    }
}
