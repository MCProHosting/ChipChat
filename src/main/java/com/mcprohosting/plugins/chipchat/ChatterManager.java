package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChatterConfig;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChatterManager {

    public static final File CHATTER_DIRECTORY = new File(ChipChat.getPlugin().getDataFolder(), "chatters");

    public static Map<String, Chatter> registeredChatters;

    public ChatterManager() {
        registeredChatters = new HashMap<String, Chatter>();
    }

    public static void loadChatter(String name) {
        ChatterConfig config = new ChatterConfig(name);
        Chatter chatter = new Chatter(name, config);

        save(chatter);

        registerChatter(name, chatter);
    }

    public static void unloadChatter(String name) {
        deregisterChatter(name);
    }

    private static void registerChatter(String name, Chatter chatter) {
        registeredChatters.put(name, chatter);
    }

    private static void deregisterChatter(String name) {
        Chatter chatter = registeredChatters.get(name);
        chatter.logout();
        save(chatter);
        registeredChatters.remove(name);
    }

    public static void save(Chatter chatter) {
        try {
            chatter.getConfig().save();
        } catch (InvalidConfigurationException e) {
            //
        }
    }

    public static void saveAll() {
        for (Chatter chatter : registeredChatters.values()) {
            save(chatter);
        }
    }

    public static Chatter getChatter(String name) {
        return registeredChatters.get(name);
    }

}
