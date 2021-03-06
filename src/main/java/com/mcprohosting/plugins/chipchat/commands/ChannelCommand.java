package com.mcprohosting.plugins.chipchat.commands;

import com.mcprohosting.plugins.chipchat.*;
import com.mcprohosting.plugins.chipchat.api.events.ChannelCreateEvent;
import com.mcprohosting.plugins.chipchat.api.events.ChannelDeleteEvent;
import com.mcprohosting.plugins.chipchat.api.events.ChannelLeaveEvent;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import com.mcprohosting.plugins.chipchat.utils.command.CommandController.*;
import org.bukkit.Bukkit;
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
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis channel already exists!"));
                return;
            }

            Channel channel = ChannelManager.loadChannel(args[0], false);

            ChannelCreateEvent event = new ChannelCreateEvent(player, channel);
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled() == false) {
                if (args.length == 2) {
                    channel.setPassword(args[1]);
                }

                channel.setOwner(player.getName());

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aChannel " + channel.getName() + " has been created!"));
            } else {
                ChannelManager.unloadChannel(channel.getName(), true);
            }
        }
    }

    @SubCommandHandler(parent = "channel",
            name = "remove",
            permission = "chipchat.channel.remove",
            permissionMessage = "You do not have permission to use this command!")
    public void channelRemove(Player player, String[] args) {
        if (args.length >= 0 && args.length <= 1) {

            Channel channel = null;
            if (args.length == 0) {
                channel = ChatterManager.getChatter(player.getName()).getActiveChannel();
            } else {
                if (ChannelManager.channelExists(args[0]) == false) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis channel does not exist!"));
                    return;
                }

                channel = ChannelManager.getChannel(args[0]);
            }

            if (channel == null) {
                return;
            }

            if (channel.getName().equalsIgnoreCase(Config.getConfig().defaultChannel)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot delete the default channel!"));
                return;
            }

            if (!(channel.isOwner(player.getName()) || player.isOp())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not the owner of this channel!"));
                return;
            }

            ChannelDeleteEvent event = new ChannelDeleteEvent(player, channel);
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled() == false) {
                channel.delete();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aChannel " + channel.getName() + " has been deleted!"));
            }
        }
    }

    @CommandHandler(name = "ch",
                    permission = "chipchat.channel.switch",
                    permissionMessage = "You do not have permission to use this command!")
    @SubCommandHandler(parent = "channel",
                      name = "switch",
                      permission = "chipchat.channel.switch",
                      permissionMessage = "You do not have permission to use this command!")
    public void channelSwitch(Player player, String[] args) {
        if (args.length >= 1 && args.length <= 2) {
            if (ChannelManager.channelExists(args[0]) == false) {
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

    @SubCommandHandler(parent = "channel",
            name = "leave",
            permission = "chipchat.channel.leave",
            permissionMessage = "You do not have permission to use this command!")
    public void channelLeave(Player player, String[] args) {
        if (args.length >= 0 && args.length <= 1) {

            Channel channel = null;
            if (args.length == 0) {
                channel = ChatterManager.getChatter(player.getName()).getActiveChannel();
            } else {
                if (ChannelManager.channelExists(args[0]) == false) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis channel does not exist!"));
                    return;
                }

                channel = ChannelManager.getChannel(args[0]);
            }

            if (channel == null) {
                return;
            }

            if (channel.getName().equalsIgnoreCase(Config.getConfig().defaultChannel)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot leave the default channel!"));
                return;
            }

            ChannelLeaveEvent event = new ChannelLeaveEvent(player, channel);
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled() == false) {
                Chatter chatter = ChatterManager.getChatter(player.getName());

                if (chatter == null) {
                    return;
                }

                chatter.leaveChannel(channel.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have left the channel " + channel.getName()));
            }
        }
    }

    @SubCommandHandler(parent = "channel",
                      name = "mute",
                      permission = "chipchat.channel.mute",
                      permissionMessage = "You do not have permission to use this command!")
    public void channelMute(Player player, String[] args) {
        Chatter chatter = ChatterManager.getChatter(player.getName());
        if (chatter == null) {
            return;
        }

        Channel channel = chatter.getActiveChannel();
        if (channel != null) {
            if (!(channel.isMod(chatter.getName()) || channel.isOwner(chatter.getName()) || player.isOp())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a mod or owner of this channel!"));
                return;
            }
        } else {
            return;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must specify a player to mute!"));
            return;
        }

        if (channel.addMuted(args[0])) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + args[0] + " has been muted!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + args[0] + " is already muted!"));
        }
    }

    @SubCommandHandler(parent = "channel",
            name = "unmute",
            permission = "chipchat.channel.unmute",
            permissionMessage = "You do not have permission to use this command!")
    public void channelUnmute(Player player, String[] args) {
        Chatter chatter = ChatterManager.getChatter(player.getName());
        if (chatter == null) {
            return;
        }

        Channel channel = chatter.getActiveChannel();
        if (channel != null) {
            if (!(channel.isMod(chatter.getName()) || channel.isOwner(chatter.getName()) || player.isOp())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a mod or owner of this channel!"));
                return;
            }
        } else {
            return;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must specify a player to unmute!"));
            return;
        }

        if (channel.removeMuted(args[0])) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + args[0] + " has been unmuted!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + args[0] + " is not muted!"));
        }
    }

    @SubCommandHandler(parent = "channel",
            name = "addmod",
            permission = "chipchat.channel.addmod",
            permissionMessage = "You do not have permission to use this command!")
    public void addMod(Player player, String[] args) {
        Chatter chatter = ChatterManager.getChatter(player.getName());
        if (chatter == null) {
            return;
        }

        Channel channel = chatter.getActiveChannel();
        if (channel != null) {
            if (!(channel.isOwner(chatter.getName()) || player.isOp())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not the owner of this channel!"));
                return;
            }
        } else {
            return;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must specify a player to make a mod!"));
            return;
        }

        if (channel.addMod(args[0])) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + args[0] + " has been made a mod in " + channel.getName() + "!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + args[0] + " is already a mod in " + channel.getName() + "!"));
        }
    }

    @SubCommandHandler(parent = "channel",
            name = "removemod",
            permission = "chipchat.channel.removemod",
            permissionMessage = "You do not have permission to use this command!")
    public void removeMod(Player player, String[] args) {
        Chatter chatter = ChatterManager.getChatter(player.getName());
        if (chatter == null) {
            return;
        }

        Channel channel = chatter.getActiveChannel();
        if (channel != null) {
            if (!(channel.isOwner(chatter.getName()) || player.isOp())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not the owner of this channel!"));
                return;
            }
        } else {
            return;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must specify a player to remove as mod!"));
            return;
        }

        if (channel.removeMod(args[0])) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + args[0] + " has been removed as mod in " + channel.getName() + "!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + args[0] + " is not a mod in " + channel.getName() + "!"));
        }
    }

}
