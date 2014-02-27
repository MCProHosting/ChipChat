package com.mcprohosting.plugins.chipchat.api.events;

import com.mcprohosting.plugins.chipchat.Channel;
import org.bukkit.entity.Player;

public class ChannelCreateEvent extends ChannelEvent {

    public ChannelCreateEvent(Player player, Channel channel) {
        super(player, channel);
    }

}
