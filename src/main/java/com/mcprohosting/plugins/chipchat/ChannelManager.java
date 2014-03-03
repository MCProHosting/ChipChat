package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChannelConfig;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    public static final File CHANNEL_DIRECTORY = new File(ChipChat.getPlugin().getDataFolder(), "channels");

    public static Map<String, Channel> registeredChannels;

    public ChannelManager() {
        registeredChannels = new HashMap<String, Channel>();
        loadChannels();
    }

    public static void loadChannels() {
        if (CHANNEL_DIRECTORY.exists() == false) {
            CHANNEL_DIRECTORY.mkdir();
        }

        loadChannel(Config.getConfig().defaultChannel, false);

        for (File subdirectory : CHANNEL_DIRECTORY.listFiles()) {
            for (File file : subdirectory.listFiles()) {
                if (file.getName().contains(".yml")) {
                    String name = file.getName().replace(".yml", "");
                    loadChannel(name, true);
                }
            }
        }
    }

    public static Channel loadChannel(String name, boolean skipDefault) {
        if (name.equals(Config.getConfig().defaultChannel) && skipDefault) {
            return null;
        }

        ChannelConfig config = new ChannelConfig(name);
        Channel channel = new Channel(name, config);

        save(channel);

        registerChannel(name, channel);

        return channel;
    }

    public static void unloadChannel(String name, boolean delete) {
        if (registeredChannels.containsKey(name.toLowerCase())) {
            deregisterChannel(name.toLowerCase());
        }

        if (delete) {
            deleteChannel(name);
        }
    }

    private static void registerChannel(String name, Channel channel) {
        registeredChannels.put(name.toLowerCase(), channel);
    }

    private static void deregisterChannel(String name) {
        Channel channel = registeredChannels.get(name.toLowerCase());
        save(channel);
        registeredChannels.remove(name.toLowerCase());
    }

    public static boolean deleteChannel(String name) {
        File file = new File(CHANNEL_DIRECTORY, name.substring(0, 1) + File.separator + name + ".yml");

        if (file.exists()) {
            file.delete();
        } else {
            return false;
        }

        return true;
    }

    public static void save(Channel channel) {
        try {
            channel.getConfig().save();
        } catch (InvalidConfigurationException e) {
            //
        }
    }

    public static void saveAll() {
        for (Channel channel : registeredChannels.values()) {
            save(channel);
        }
    }

    public static Channel getChannel(String name) {
        return registeredChannels.get(name.toLowerCase());
    }

    public static Channel getDefaultChannel() {
        return registeredChannels.get(Config.getConfig().defaultChannel);
    }

    public static boolean channelExists(String name) {
        return registeredChannels.containsKey(name.toLowerCase());
    }

}
