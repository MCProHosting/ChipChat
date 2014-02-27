package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChannelConfig;
import com.mcprohosting.plugins.chipchat.configuration.models.ChannelData;

public class Channel extends ChannelData {

    private ChannelConfig config;

    public Channel() {
        super();
    }

    public Channel(String name) {
        super(name);
    }

    public Channel(String name, String password) {
        super(name, password);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addChatter(String chatter) {
        this.chatters.add(chatter);
    }

    public void removeChatter(String chatter) {
        this.chatters.remove(chatter);
    }

    public boolean containsUser(String name) {
        return this.chatters.contains(name);
    }

    public void setConfig(ChannelConfig config) {
        this.config = config;
    }

    public ChannelConfig getConfig() {
        return config;
    }

}
