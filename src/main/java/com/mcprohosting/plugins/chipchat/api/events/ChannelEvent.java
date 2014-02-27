package com.mcprohosting.plugins.chipchat.api.events;

import com.mcprohosting.plugins.chipchat.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChannelEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Channel channel;
    private boolean cancelled;

    public ChannelEvent(Player player, Channel channel) {
        this.player = player;
        this.channel = channel;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
