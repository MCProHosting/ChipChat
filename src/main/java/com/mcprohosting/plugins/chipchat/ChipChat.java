package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.api.command.CommandHandler;
import com.mcprohosting.plugins.chipchat.commands.Channel;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ChipChat extends JavaPlugin {

    private static ChipChat plugin;
    private static Config config;
    private static CommandHandler commandHandler;

    public void onEnable() {
        plugin = this;
        config = new Config(this);
        commandHandler = new CommandHandler(this);

        registerCommands();
    }

    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.handleCommand(sender, label, command, args);
    }

    public static ChipChat getPlugin() {
        return plugin;
    }

    private void registerCommands() {
        commandHandler.registerCommands(new Channel(this));
    }

}
