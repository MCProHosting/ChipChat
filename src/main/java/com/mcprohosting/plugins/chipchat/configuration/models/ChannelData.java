package com.mcprohosting.plugins.chipchat.configuration.models;

import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigObject;

import java.util.ArrayList;

public class ChannelData extends ConfigObject {

    public String name = "";
    public String password = "";
    public String permission = "";
    public String owner = "";
    public ArrayList<String> mods = new ArrayList<String>();
    public String color = "&2";
    public String format = "&8[%c%%n%&8] &7%p%&8: &7%m%";
    public ArrayList<String> muted = new ArrayList<>();

    public ChannelData() {}

    public ChannelData(String name) {
        this.name = name;
    }

}
