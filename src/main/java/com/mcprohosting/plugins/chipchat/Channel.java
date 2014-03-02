package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.api.events.ChannelMessageEvent;
import com.mcprohosting.plugins.chipchat.configuration.ChannelConfig;
import com.mcprohosting.plugins.chipchat.configuration.models.ChannelData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Channel {

    private String name;
    private ChannelConfig config;
    private ChannelData data;
    private ArrayList<Chatter> chatters;

    public Channel(String name, ChannelConfig config) {
        this.name = name;
        this.config = config;
        this.data = config.getChannelData();
        this.chatters = new ArrayList<Chatter>();
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

    public void addChatter(Chatter chatter) {
        chatters.add(chatter);
    }

    public void removeChatter(Chatter chatter) {
        chatters.remove(chatter);
    }

    public boolean containsUser(Chatter name) {
        return chatters.contains(name);
    }

    public void setConfig(ChannelConfig config) {
        this.config = config;
    }

    public ChannelConfig getConfig() {
        return config;
    }

    public void handleMessageEvent(ChannelMessageEvent event) {
        for (Chatter chatter : chatters) {
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

        event.getPlayer().sendMessage("Formatted message!");

        return message;
    }

}
