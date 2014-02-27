package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChannelConfig;
import com.mcprohosting.plugins.chipchat.configuration.models.ChannelData;

public class Channel {

    private String name;
    private ChannelConfig config;
    private ChannelData data;

    public Channel(String name, ChannelConfig config) {
        this.name = name;
        this.config = config;
        this.data = config.getChannelData();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.data.password;
    }

    public void setPassword(String password) {
        this.data.password = password;
    }

    public void addChatter(String chatter) {
        this.data.chatters.add(chatter);
    }

    public void removeChatter(String chatter) {
        this.data.chatters.remove(chatter);
    }

    public boolean containsUser(String name) {
        return this.data.chatters.contains(name);
    }

    public void setConfig(ChannelConfig config) {
        this.config = config;
    }

    public ChannelConfig getConfig() {
        return config;
    }

}
