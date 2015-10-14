package com.wow.loginserver;

import com.wow.loginserver.network.LoginClient;
import com.wow.loginserver.network.serverpackets.Init;
import com.wow.utils.IPv4Filter;
import com.wow.utils.mmocore.*;

import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
public class SelectorHelper implements IMMOExecutor<LoginClient>, IClientFactory<LoginClient>, IAcceptFilter
{
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    private final ThreadPoolExecutor _generalPacketsThreadPool;
    private final IPv4Filter _ipv4filter;

    public SelectorHelper()
    {
        _generalPacketsThreadPool = new ThreadPoolExecutor(4, 6, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        _ipv4filter = new IPv4Filter();
    }

    @Override
    public boolean accept(SocketChannel sc)
    {
        return _ipv4filter.accept(sc);
    }

    @Override
    public LoginClient create(MMOConnection<LoginClient> con)
    {
        LoginClient client = new LoginClient(con);
        client.sendPacket(new Init(client));
        return client;
    }

    @Override
    public void execute(ReceivablePacket<LoginClient> packet)
    {
        _generalPacketsThreadPool.execute(packet);
    }
}
