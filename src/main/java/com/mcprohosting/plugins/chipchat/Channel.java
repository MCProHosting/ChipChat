package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.api.events.ChannelMessageEvent;
import com.mcprohosting.plugins.chipchat.configuration.ChannelConfig;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import com.mcprohosting.plugins.chipchat.configuration.models.ChannelData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Channel {

    private String name;
    private ChannelConfig config;
    private ChannelData data;
    private Map<String, Chatter> chatters;

    public Channel(String name, ChannelConfig config) {
        this.name = name;
        this.config = config;
        this.data = config.getChannelData();
        this.chatters = new HashMap<String, Chatter>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return data.password;
    }

    public void setPassword(String password) {
        this.data.password = password;
    }

    public boolean canJoin(String password) {
        if (getPassword().equals(password) == true) {
            return true;
        }

        return false;
    }

    public void addChatter(Chatter chatter) {
        chatters.put(chatter.getName().toLowerCase(), chatter);
    }

    public void removeChatter(Chatter chatter) {
        chatters.remove(chatter.getName().toLowerCase());
    }

    public boolean containsUser(Chatter chatter) {
        return chatters.containsKey(chatter.getName().toLowerCase());
    }

    public void setConfig(ChannelConfig config) {
        this.config = config;
    }

    public ChannelConfig getConfig() {
        return config;
    }

    public void handleMessageEvent(ChannelMessageEvent event) {
        if (data.permission.equals("") == false && ChipChat.getPerms().has(event.getPlayer(), data.permission) == false) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to talk in this channel!"));
            return;
        }

        if (data.muted.contains(event.getPlayer().getName())) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are muted and cannot speak in this channel!"));
            return;
        }

        for (Chatter chatter : chatters.values()) {
            Player player = Bukkit.getPlayer(chatter.getName());
            if (player != null) {
                player.sendMessage(getFormattedMessage(event));
            }
        }
    }

    public String getFormattedMessage(ChannelMessageEvent event) {
        String message = data.format;
        message = message.replace("%c%", data.color);
        message = message.replace("%n%", data.name);
        message = message.replace("%p%", event.getPlayer().getName());
        message = message.replace("%m%", event.getMessage());
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }

    public boolean setOwner(String name) {
        if (data.owner.equals(name.toLowerCase()) == false) {
            data.owner = name.toLowerCase();
            return true;
        }

        return false;
    }

    public boolean isOwner(String name) {
        return data.owner.equals(name);
    }

    public boolean addMod(String name) {
        if (data.mods.contains(name.toLowerCase()) == false) {
            data.mods.add(name.toLowerCase());
            return true;
        }

        return false;
    }

    public boolean removeMod(String name) {
        if (data.mods.contains(name.toLowerCase())) {
            data.mods.remove(name.toLowerCase());
            return true;
        }

        return false;
    }

    public boolean isMod(String name) {
        return data.mods.contains(name);
    }

    public boolean addMuted(String name) {
        if (data.muted.contains(name) == false) {
            data.muted.add(name.toLowerCase());
            return true;
        }

        return false;
    }

    public boolean removeMuted(String name) {
        if (data.muted.contains(name.toLowerCase())) {
            data.muted.remove(name.toLowerCase());
            return true;
        }

        return false;
    }

    public void delete() {
        for (Chatter chatter : chatters.values()) {
            chatter.setActiveChannel(Config.getConfig().defaultChannel);
            chatter.leaveChannel(this.name);

            ChatterManager.save(chatter);
        }

        ChannelManager.unloadChannel(name, true);
    }

}
