package com.markbromell.manhunt;

import com.markbromell.manhunt.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Manhunt extends JavaPlugin {
    private PlayerRoleManager playerRoleManager;

    @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin has stopped.");
    }

    @Override
    public void onEnable() {
        playerRoleManager = new PlayerRoleManager();
        setCommandExecutors();
    }

    private void setCommandExecutors() {
        CommandHunted hunted = new CommandHunted(playerRoleManager, this);
        CommandHunter hunter = new CommandHunter(playerRoleManager, this);
        CommandStart start = new CommandStart(playerRoleManager, this);
        CommandEnd end = new CommandEnd(playerRoleManager, this);

        Objects.requireNonNull(getCommand("hunted")).setExecutor(hunted);
        Objects.requireNonNull(getCommand("hunter")).setExecutor(hunter);
        Objects.requireNonNull(getCommand("start")).setExecutor(start);
        Objects.requireNonNull(getCommand("end")).setExecutor(end);
    }
}
