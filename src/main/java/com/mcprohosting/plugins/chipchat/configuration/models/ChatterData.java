package com.mcprohosting.plugins.chipchat.configuration.models;

import com.mcprohosting.plugins.chipchat.configuration.Config;
import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigObject;

import java.util.ArrayList;

public class ChatterData extends ConfigObject {

    public String name = "";
    public String activeChannel = Config.getConfig().defaultChannel;
    public ArrayList<String> joined = new ArrayList<String>(){{
        add(Config.getConfig().defaultChannel);
    }};
    public boolean mutedGlobally = false;

    public ChatterData() {}

    public ChatterData(String name) {
        this.name = name;
    }

}
