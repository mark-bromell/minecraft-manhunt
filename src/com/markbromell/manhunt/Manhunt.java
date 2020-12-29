package com.markbromell.manhunt;

import com.markbromell.manhunt.command.*;
import com.markbromell.manhunt.listener.HuntedMoveListener;
import com.markbromell.manhunt.listener.HunterRespawnListener;
import com.markbromell.manhunt.listener.PlayerJoinListener;
import com.markbromell.manhunt.persistence.PlayerRoleManager;
import com.markbromell.manhunt.persistence.PlayerRoleYamlPersistence;
import com.markbromell.manhunt.persistence.RoleManager;
import com.markbromell.manhunt.persistence.RolePersistence;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/** Main plugin class */
public class Manhunt extends JavaPlugin {
    private RolePersistence rolePersistence;
    private RoleManager roleManager;

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
        Yaml yaml = new Yaml();
        rolePersistence = new PlayerRoleYamlPersistence(yaml, getServer());
        roleManager = new PlayerRoleManager(rolePersistence);
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
            add(new HuntedMoveListener(roleManager, pluginManager));
            add(new HunterRespawnListener(roleManager));
            add(new PlayerJoinListener(rolePersistence, roleManager));
        }};

        listeners.forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    /**
     * Sets the command executors for the Manhunt plugin.
     */
    private void setCommandExecutors() {
        CommandHunted hunted = new CommandHunted(roleManager, getServer());

        ParentCommand hunter = new ParentCommand(new HashMap<String, TabExecutor>() {{
            put("add", new CommandHunterAdd(roleManager, getServer()));
            put("remove", new CommandHunterRemove(roleManager, getServer()));
            put("list", new CommandHunterList(roleManager));
        }});

        Objects.requireNonNull(getCommand("hunted")).setExecutor(hunted);
        Objects.requireNonNull(getCommand("hunter")).setExecutor(hunter);
    }
}
