package com.markbromell.manhunt;

import com.markbromell.manhunt.command.*;
import com.markbromell.manhunt.listener.HuntedMoveListener;
import com.markbromell.manhunt.listener.HunterRespawnListener;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/** Main plugin class */
public class Manhunt extends JavaPlugin {
    private PlayerRoleManager playerRoleManager;

    /**
     * Gets called when the plugin is stopped.
     */
    @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin has stopped.");
    }

    /**
     * Gets called when the plugin is loaded.
     */
    @Override
    public void onEnable() {
        playerRoleManager = new PlayerRoleManager();
        registerListeners();
        setCommandExecutors();
    }

    /**
     * Registers all the listeners for the Manhunt plugin.
     */
    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        HandlerList.unregisterAll(this);

        List<Listener> listeners = new ArrayList<Listener>() {{
            add(new HuntedMoveListener(playerRoleManager, pluginManager));
            add(new HunterRespawnListener(playerRoleManager));
        }};

        listeners.forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    /**
     * Sets the command executors for the Manhunt plugin.
     */
    private void setCommandExecutors() {
        CommandHunted hunted = new CommandHunted(playerRoleManager, this);

        ParentCommand hunter = new ParentCommand(new HashMap<String, TabExecutor>() {{
            put("add", new CommandHunterAdd(playerRoleManager, Manhunt.this));
            put("remove", new CommandHunterRemove(playerRoleManager, Manhunt.this));
            put("list", new CommandHunterList(playerRoleManager, Manhunt.this));
        }});

        Objects.requireNonNull(getCommand("hunted")).setExecutor(hunted);
        Objects.requireNonNull(getCommand("hunter")).setExecutor(hunter);
    }
}
