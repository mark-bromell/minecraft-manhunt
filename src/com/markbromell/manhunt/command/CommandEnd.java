package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.event.ListenerManager;
import com.markbromell.manhunt.listener.PlayerAbsHorizontalMoveListener;
import com.markbromell.manhunt.listener.PlayerMoveListener;
import com.markbromell.manhunt.listener.PlayerRespawnListener;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CommandEnd implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Server server;
    private final Plugin plugin;
    private final PluginManager pluginManager;

    public CommandEnd(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
        this.plugin = plugin;
        this.pluginManager = this.server.getPluginManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        ListenerManager.getInstance().removeAll();
        return true;
    }
}
