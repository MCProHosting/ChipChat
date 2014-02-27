package com.mcprohosting.plugins.chipchat.api.events;

import com.mcprohosting.plugins.chipchat.Channel;
import org.bukkit.entity.Player;

public class ChannelEditEvent extends ChannelEvent {

    public ChannelEditEvent(Player player, Channel channel) {
        super(player, channel);
    }

}
