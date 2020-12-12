package com.markbromell.manhunt;

import com.markbromell.manhunt.command.CommandHunted;
import com.markbromell.manhunt.command.CommandHunter;
import com.markbromell.manhunt.listener.PlayerMoveListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Manhunt extends JavaPlugin {
    @Override
    public void onEnable() {
        setEvents();
        setCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin has stopped.");
    }

    private void setEvents() {
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
    }

    private void setCommands() {
        PlayerRoleManager playerRoleManager = new PlayerRoleManager();

        Objects.requireNonNull(getCommand("hunted")).setExecutor(new CommandHunted(playerRoleManager));
        Objects.requireNonNull(getCommand("hunter")).setExecutor(new CommandHunter(playerRoleManager));
    }
}
