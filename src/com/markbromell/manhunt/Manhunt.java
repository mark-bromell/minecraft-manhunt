package com.markbromell.manhunt;

import com.markbromell.manhunt.command.CommandHunted;
import com.markbromell.manhunt.command.CommandHunter;
import com.markbromell.manhunt.listener.PlayerMoveListener;
import com.markbromell.manhunt.listener.PlayerRespawnListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Manhunt extends JavaPlugin {
    private PlayerRoleManager playerRoleManager;

    @Override
    public void onEnable() {
        playerRoleManager = new PlayerRoleManager();
        setEvents();
        setCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin has stopped.");
    }

    private void setEvents() {
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(playerRoleManager), this);
    }

    private void setCommands() {
        Objects.requireNonNull(getCommand("hunted")).setExecutor(new CommandHunted(playerRoleManager));
        Objects.requireNonNull(getCommand("hunter")).setExecutor(new CommandHunter(playerRoleManager));
    }
}
