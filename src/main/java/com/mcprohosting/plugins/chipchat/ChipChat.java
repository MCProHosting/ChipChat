package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.listeners.PlayerListener;
import com.mcprohosting.plugins.chipchat.utils.command.CommandHandler;
import com.mcprohosting.plugins.chipchat.commands.Channel;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ChipChat extends JavaPlugin {

    private static ChipChat plugin;
    private static Config config;
    private static CommandHandler commandHandler;
    private ChannelManager channelManager;
    private ChatterManager chatterManager;

    public void onEnable() {
        plugin = this;
        config = new Config(this);
        commandHandler = new CommandHandler(this);
        channelManager = new ChannelManager();
        chatterManager = new ChatterManager();

        registerCommands();
        registerListeners();
    }

    public void onDisable() {
        ChannelManager.saveAll();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.handleCommand(sender, label, command, args);
    }

    public static ChipChat getPlugin() {
        return plugin;
    }

    private void registerCommands() {
        commandHandler.registerCommands(new Channel(this));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public Config getConfiguration() {
        return config;
    }

    public ChatterManager getChatterManager() {
        return chatterManager;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }

}
