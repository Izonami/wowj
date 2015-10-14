package org.wowj.auth.config;


import org.springframework.stereotype.Component;
import org.wowj.commons.configuration.ConfigEngine;
import org.wowj.commons.configuration.ConfigField;

import java.net.InetSocketAddress;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
@Component
public class Config extends ConfigEngine
{
    private static final String CONFIG_FILE = "conf/network/network.properties";

    @ConfigField(config = "network", fieldName = "NetworkClientAddress", value = "*:3724")
    public InetSocketAddress CLIENT_ADDRESS;

    @ConfigField(config = "network", fieldName = "UpdateRealmlistInterval", value = "60")
    public int UPDATE_INTERVAL;

    public Config()
    {
        loadConfig(Config.class, "network", CONFIG_FILE);
    }
}
