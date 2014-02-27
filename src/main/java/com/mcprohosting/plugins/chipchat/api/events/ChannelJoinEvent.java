package com.mcprohosting.plugins.chipchat.api.events;

import com.mcprohosting.plugins.chipchat.Channel;
import org.bukkit.entity.Player;

public class ChannelJoinEvent extends ChannelEvent {

    public ChannelJoinEvent(Player player, Channel channel) {
        super(player, channel);
    }

}
