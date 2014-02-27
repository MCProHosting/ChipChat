package com.mcprohosting.plugins.chipchat.configuration;

import com.mcprohosting.plugins.chipchat.ChatterManager;
import com.mcprohosting.plugins.chipchat.configuration.models.ChatterData;
import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigModel;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;

public class ChatterConfig extends ConfigModel {

    public ChatterData chatter;

    public ChatterConfig(String name) {
        CONFIG_FILE = new File(ChatterManager.CHATTER_DIRECTORY, name.substring(0, 1).toUpperCase() + File.separator + name + ".yml");
        CONFIG_HEADER = "Player Config File";

        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (chatter.name.equals("")) {
            chatter = new ChatterData(name);
        }
    }

    public ChatterData getChatterData() {
        return chatter;
    }

}
