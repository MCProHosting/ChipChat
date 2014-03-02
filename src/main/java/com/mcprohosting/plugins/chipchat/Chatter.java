package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChatterConfig;
import com.mcprohosting.plugins.chipchat.configuration.models.ChatterData;

public class Chatter {

    private String name;
    private ChatterConfig config;
    private ChatterData data;

    public Chatter(String name, ChatterConfig config) {
        this.name = name;
        this.config = config;
        this.data = config.getChatterData();

        for (String ch : data.joined) {
            Channel channel = ChannelManager.getChannel(ch);
            if (channel == null) {
                continue;
            }
            channel.addChatter(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setMuted() {
        // TODO Global Mute
    }

    public void setMuted(Channel channel) {
        if (channel.containsUser(this)) {
            this.data.muted.add(channel.getName());
        }
    }

    public boolean isMuted(Channel channel) {
        return this.data.muted.contains(channel.getName());
    }

    public boolean isMuted() {
        return this.data.mutedGlobally;
    }

    public ChatterConfig getConfig() {
        return config;
    }

    public Channel getActiveChannel() {
        return ChannelManager.getDefaultChannel();
    }

}
