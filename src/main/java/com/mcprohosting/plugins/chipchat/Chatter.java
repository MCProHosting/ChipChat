package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.configuration.ChatterConfig;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import com.mcprohosting.plugins.chipchat.configuration.models.ChatterData;
import net.minecraft.util.io.netty.channel.ChannelHandler;

public class Chatter {

    private String name;
    private ChatterConfig config;
    private ChatterData data;

    public Chatter(String name, ChatterConfig config) {
        this.name = name;
        this.config = config;
        this.data = config.getChatterData();

        if (getActiveChannel() == null) {
            this.data.activeChannel = Config.getConfig().defaultChannel;
        }

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

    public boolean isMuted() {
        return this.data.mutedGlobally;
    }

    public ChatterConfig getConfig() {
        return config;
    }

    public Channel getActiveChannel() {
        return ChannelManager.getChannel(data.activeChannel);
    }

    public void setActiveChannel(String activeChannel) {
        data.activeChannel = activeChannel;
    }

    public void registerJoinedChannel(String channel) {
        data.joined.add(channel);
    }

    public void logout() {
        for (String ch : data.joined) {
            Channel channel = ChannelManager.getChannel(ch);
            if (channel == null) {
                data.joined.remove(ch);
                return;
            }

            channel.removeChatter(this);
        }
    }

    public void leaveChannel(String channel) {
        if (data.activeChannel.equalsIgnoreCase(channel)) {
            data.activeChannel = Config.getConfig().defaultChannel;
        }

        data.joined.remove(channel);

        ChatterManager.save(this);
    }

}
