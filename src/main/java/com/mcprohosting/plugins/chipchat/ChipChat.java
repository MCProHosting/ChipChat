package com.mcprohosting.plugins.chipchat;

import com.mcprohosting.plugins.chipchat.listeners.PlayerListener;
import com.mcprohosting.plugins.chipchat.commands.ChannelCommand;
import com.mcprohosting.plugins.chipchat.configuration.Config;
import com.mcprohosting.plugins.chipchat.utils.command.CommandController;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;

public class ChipChat extends JavaPlugin {

    private static ChipChat plugin;
    private static Config config;
    private ChannelManager channelManager;
    private ChatterManager chatterManager;
    private static Permission perms;

    public void onEnable() {
        plugin = this;
        config = new Config(this);
        channelManager = new ChannelManager();
        chatterManager = new ChatterManager();

        registerCommands();
        registerListeners();

        if (!setupPermissions()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }

    public void onDisable() {
        ChannelManager.saveAll();
        ChatterManager.saveAll();
    }

    public static ChipChat getPlugin() {
        return plugin;
    }

    private void registerCommands() {
        CommandController.registerCommands(this, new ChannelCommand(this));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
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

    public static Permission getPerms() {
        return perms;
    }

}
