package com.mcprohosting.plugins.chipchat.listeners;

import com.mcprohosting.plugins.chipchat.ChipChat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        ChipChat.getPlugin().getChatterManager().loadChatter(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onPlayerQuit(PlayerQuitEvent event) {
        ChipChat.getPlugin().getChatterManager().unloadChatter(event.getPlayer().getName());
    }

}
