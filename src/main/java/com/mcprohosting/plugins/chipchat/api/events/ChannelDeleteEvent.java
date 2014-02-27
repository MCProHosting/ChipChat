package com.mcprohosting.plugins.chipchat.api.events;

import com.mcprohosting.plugins.chipchat.Channel;
import org.bukkit.entity.Player;

public class ChannelDeleteEvent extends ChannelEvent {

    public ChannelDeleteEvent(Player player, Channel channel) {
        super(player, channel);
    }

}
