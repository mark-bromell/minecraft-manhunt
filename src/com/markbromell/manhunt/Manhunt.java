package com.markbromell.manhunt;

import com.markbromell.manhunt.command.CommandHunted;
import com.markbromell.manhunt.command.CommandHunter;
import com.markbromell.manhunt.listener.PlayerMoveListener;
import com.markbromell.manhunt.listener.PlayerRespawnListener;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Manhunt extends JavaPlugin {
    private PlayerRoleManager playerRoleManager;
    private PluginManager pluginManager;

    @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin has stopped.");
    }

    @Override
    public void onEnable() {
        playerRoleManager = new PlayerRoleManager();
        pluginManager = getServer().getPluginManager();
        setEvents();
        setCommandExecutors();
    }

    private void setEvents() {
        pluginManager.registerEvents(new PlayerMoveListener(pluginManager), this);
        pluginManager.registerEvents(new PlayerRespawnListener(playerRoleManager), this);
    }

    private void setCommandExecutors() {
        CommandHunted commandHunted = new CommandHunted(playerRoleManager, this);
        CommandHunter commandHunter = new CommandHunter(playerRoleManager, this);

        Objects.requireNonNull(getCommand("hunted")).setExecutor(commandHunted);
        Objects.requireNonNull(getCommand("hunter")).setExecutor(commandHunter);
    }
}
