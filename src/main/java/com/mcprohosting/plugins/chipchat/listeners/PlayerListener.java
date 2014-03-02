package com.mcprohosting.plugins.chipchat.listeners;

import com.mcprohosting.plugins.chipchat.Chatter;
import com.mcprohosting.plugins.chipchat.ChatterManager;
import com.mcprohosting.plugins.chipchat.api.events.ChannelMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        ChatterManager.loadChatter(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onPlayerQuit(PlayerQuitEvent event) {
        ChatterManager.unloadChatter(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Chatter chatter = ChatterManager.getChatter(event.getPlayer().getName());
        ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(player, chatter.getActiveChannel(), event.getMessage());

        if (channelMessageEvent == null) {
            event.getPlayer().sendMessage("Why is this null?");
        }
        Bukkit.getPluginManager().callEvent(channelMessageEvent);

        if (channelMessageEvent.isCancelled() == false) {
            chatter.getActiveChannel().handleMessageEvent(channelMessageEvent);
        }

        event.setCancelled(true);
    }

}
