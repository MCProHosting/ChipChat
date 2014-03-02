package com.mcprohosting.plugins.chipchat.commands;

import com.mcprohosting.plugins.chipchat.*;
import com.mcprohosting.plugins.chipchat.utils.command.CommandController.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChannelCommand {

    private ChipChat plugin;

    public ChannelCommand(ChipChat plugin) {
        this.plugin = plugin;
    }

    @SubCommandHandler(parent = "channel",
            name = "create",
            permission = "chipchat.channel.create",
            permissionMessage = "You do not have permission to use this command!")
    public void channelCreate(Player player, String[] args) {
        if (args.length >= 1 && args.length <= 2) {
            if (ChannelManager.channelExists(args[0])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "This channel already exists!"));
                return;
            }

            Channel channel = ChannelManager.loadChannel(args[0], false);

            if (args.length == 2) {
                channel.setPassword(args[1]);
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Channel " + channel.getName() + " has been created!"));
        }
    }

    @SubCommandHandler(parent = "channel",
                      name = "switch",
                      permission = "chipchat.channel.switch",
                      permissionMessage = "You do not have permission to use this command!")
    public void channelSwitch(Player player, String[] args) {
        if (args.length >= 1 && args.length <= 2) {
            if (ChannelManager.channelExists(args[0])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat channel does not exists!"));
                return;
            }

            Channel channel = ChannelManager.getChannel(args[0]);
            String password = "";
            if (args.length == 2) {
                password = args[1];
            }

            if (channel.canJoin(password) == false) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must enter the correct channel password to join!"));
                return;
            }

            Chatter chatter = ChatterManager.getChatter(player.getName());

            if (chatter.getActiveChannel().equals(channel)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou are already talking in " + channel.getName()));
                return;
            }

            chatter.setActiveChannel(channel.getName());

            if (chatter.getConfig().getChatterData().joined.contains(channel.getName())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou are now talking in " + channel.getName()));
                return;
            }

            chatter.registerJoinedChannel(channel.getName());
            channel.addChatter(chatter);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have joined " + channel.getName()));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must specify a channel!"));
        }
    }

}
