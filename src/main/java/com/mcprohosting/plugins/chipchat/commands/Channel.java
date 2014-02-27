package com.mcprohosting.plugins.chipchat.commands;

import com.mcprohosting.plugins.chipchat.ChipChat;
import com.mcprohosting.plugins.chipchat.utils.command.Command;
import com.mcprohosting.plugins.chipchat.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Channel {

    private ChipChat plugin;

    public Channel(ChipChat plugin) {
        this.plugin = plugin;
    }

    @Command(name = "channel",
            aliases = {"ch"},
            description = "Displays current channels information.",
            usage = "/channel",
            permission = "chipchat.channel",
            noPerm = "&cYou do not have permission to use this command!")
    public void channel(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aWIP"));
    }

}
