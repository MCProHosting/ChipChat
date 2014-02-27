package com.mcprohosting.plugins.chipchat.configuration;

import com.mcprohosting.plugins.chipchat.Channel;
import com.mcprohosting.plugins.chipchat.ChannelManager;
import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigModel;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;

public class ChannelConfig extends ConfigModel {

    public Channel channel;

    public ChannelConfig(String name) {
        CONFIG_FILE = new File(ChannelManager.CHANNEL_DIRECTORY + name.substring(0, 1).toUpperCase() + File.separator + name + ".yml");
        CONFIG_HEADER = "Channel Config File";

        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        channel = new Channel(name);
        channel.setConfig(this);
    }

}
