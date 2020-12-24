package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.listener.PlayerAbsHorizontalMoveListener;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class CommandHunted implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Plugin plugin;
    private final Server server;
    private final PluginManager pluginManager;

    public CommandHunted(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.pluginManager = this.server.getPluginManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        Player hunted = server.getPlayer(args[0]);
        playerRoleManager.setHunted(hunted);
        pluginManager.registerEvents(
                new PlayerAbsHorizontalMoveListener(playerRoleManager),
                Objects.requireNonNull(plugin)
        );
        return true;
    }
}
