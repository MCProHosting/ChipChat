package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChannelConfig;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    public static final File CHANNEL_DIRECTORY = new File(ChipChat.getPlugin().getDataFolder(), "channels");

    public Map<String, Channel> registeredChannels;

    public ChannelManager() {
        registeredChannels = new HashMap<String, Channel>();
        loadChannels();
    }

    public void loadChannels() {
        for (File subdirectory : CHANNEL_DIRECTORY.listFiles()) {
            for (File file : subdirectory.listFiles()) {
                if (file.getName().contains(".yml")) {
                    String name = file.getName().replace(".yml", "");
                    ChannelConfig config = new ChannelConfig(file);
                    Channel channel = new Channel(name, config);
                    registerChannel(name, channel);
                }
            }
        }
    }

    public void unloadChannel(String name) {
        deregisterChannel(name);
    }

    private void registerChannel(String name, Channel channel) {
        registeredChannels.put(name, channel);
    }

    private void deregisterChannel(String name) {
        try {
            Channel channel = registeredChannels.get(name);
            save(channel);
            registeredChannels.remove(name);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void save(Channel channel) throws InvalidConfigurationException {
        channel.getConfig().save();
    }

}
