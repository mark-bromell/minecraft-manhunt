package com.markbromell.manhunt;

import com.markbromell.manhunt.command.*;
import com.markbromell.manhunt.listener.HuntedMoveListener;
import com.markbromell.manhunt.listener.HunterRespawnListener;
import com.markbromell.manhunt.listener.PlayerJoinListener;
import com.markbromell.manhunt.persistence.PlayerRoleManager;
import com.markbromell.manhunt.persistence.PlayerRoleYamlPersistence;
import com.markbromell.manhunt.persistence.RoleManager;
import com.markbromell.manhunt.persistence.RolePersistence;
import org.bukkit.ChatColor;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/** Main plugin class */
public class Manhunt extends JavaPlugin {
    public static final Path BASE_PATH = Paths.get("plugins", "manhunt");

    /** Formatters for the minecraft chat messages. */
    public static final String HEADING = ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC;
    public static final String INFO = ChatColor.GREEN + "" + ChatColor.ITALIC;
    public static final String ERROR = ChatColor.RED + "" + ChatColor.ITALIC;

    private final Logger log;
    private RolePersistence rolePersistence;
    private RoleManager roleManager;

    public Manhunt() {
        this.log = getLogger();
    }

    @Override
    public void onEnable() {
        initBasePath();
        rolePersistence = new PlayerRoleYamlPersistence(new Yaml(), getServer());
        roleManager = new PlayerRoleManager(rolePersistence);
        registerListeners();
        setCommandExecutors();
    }

    /**
     * Create the base path of the plugin if it does not already exist.
     */
    private void initBasePath() {
        if (!Files.exists(BASE_PATH)) {
            try {
                log.info("Trying to create plugin base path");
                Files.createDirectory(BASE_PATH);
            } catch (IOException e) {
                log.warning("Failed to create base plugin path");
            }
        }
    }

    /**
     * Registers all the listeners for the Manhunt plugin.
     */
    private void registerListeners() {
        log.info("Registering listeners");
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
        log.info("Setting command executors");
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
