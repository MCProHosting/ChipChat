package com.mcprohosting.plugins.chipchat.configuration;

import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigModel;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Config extends ConfigModel {

    private static Config config;

    public Config(Plugin plugin) {
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
        CONFIG_HEADER = "ChipChat Configuration File";

        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        config = this;
    }

    public static Config getConfig() {
        return config;
    }

}
