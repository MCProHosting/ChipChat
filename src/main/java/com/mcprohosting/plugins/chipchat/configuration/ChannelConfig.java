package com.mcprohosting.plugins.chipchat.configuration;

import com.mcprohosting.plugins.chipchat.ChannelManager;
import com.mcprohosting.plugins.chipchat.configuration.models.ChannelData;
import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigModel;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;

public class ChannelConfig extends ConfigModel {

    public ChannelData channel = new ChannelData();

    public ChannelConfig(String name) {
        CONFIG_FILE = new File(ChannelManager.CHANNEL_DIRECTORY, name.substring(0, 1).toUpperCase() + File.separator + name + ".yml");
        CONFIG_HEADER = "Channel Config File";

        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (channel.name.equals("")) {
            channel.name = name;
        }
    }

    public ChannelConfig(File file) {
        CONFIG_FILE = file;
        CONFIG_HEADER = "Channel Config File";

        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public ChannelData getChannelData() {
        return channel;
    }

}
