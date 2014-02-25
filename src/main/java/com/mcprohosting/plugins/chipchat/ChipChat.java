package com.mcprohosting.plugins.chipchat;

import org.bukkit.plugin.java.JavaPlugin;

public class ChipChat extends JavaPlugin {

    private static ChipChat plugin;

    public void onEnable() {
        plugin = this;
    }

    public void onDisable() {}

    public static ChipChat getPlugin() {
        return plugin;
    }

}
