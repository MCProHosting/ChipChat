package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChatterConfig;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChatterManager {

    public static final File CHATTER_DIRECTORY = new File(ChipChat.getPlugin().getDataFolder(), "chatters");

    public Map<String, Chatter> registeredChatters;

    public ChatterManager() {
        registeredChatters = new HashMap<String, Chatter>();
    }

    public void loadChatter(String name) {
        ChatterConfig config = new ChatterConfig(name);
        Chatter chatter = new Chatter(name, config);
        registerChatter(name, chatter);
    }

    public void unloadChatter(String name) {
        deregisterChatter(name);
    }

    private void registerChatter(String name, Chatter chatter) {
        registeredChatters.put(name, chatter);
    }

    private void deregisterChatter(String name) {
        try {
            Chatter chatter = registeredChatters.get(name);
            save(chatter);
            registeredChatters.remove(name);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void save(Chatter chatter) throws InvalidConfigurationException {
        chatter.getConfig().save();
    }

}
