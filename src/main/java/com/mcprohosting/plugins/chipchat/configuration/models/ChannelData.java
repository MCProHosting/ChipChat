package com.mcprohosting.plugins.chipchat.configuration.models;

import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigObject;

import java.util.ArrayList;

public class ChannelData extends ConfigObject {

    public String name;
    public String password;
    public ArrayList<String> chatters;

    public ChannelData() {}

    public ChannelData(String name) {
        this.name = name;
    }

    public ChannelData(String name, String password) {
        this(name);

        this.password = password;
        this.chatters = new ArrayList<String>();
    }

}
