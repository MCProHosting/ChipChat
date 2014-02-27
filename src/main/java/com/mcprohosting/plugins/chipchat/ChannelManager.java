package com.mcprohosting.plugins.chipchat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    public static final File CHANNEL_DIRECTORY = new File(ChipChat.getPlugin().getDataFolder(), "channels");

    public Map<String, Channel> registeredChannels;

    public ChannelManager() {
        registeredChannels = new HashMap<String, Channel>();
    }

    public void loadChannels() {
        for (File file : CHANNEL_DIRECTORY.listFiles()) {
            //
        }
    }

}
