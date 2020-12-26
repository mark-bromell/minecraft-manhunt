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
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class CommandStart implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Server server;
    private final Plugin plugin;
    private final PluginManager pluginManager;

    public CommandStart(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
        this.plugin = plugin;
        this.pluginManager = this.server.getPluginManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        registerEvents();
        return true;
    }

    private void registerEvents() {
        List<Listener> listeners = new ArrayList<Listener>() {{
            add(new PlayerAbsHorizontalMoveListener(playerRoleManager));
            add(new PlayerMoveListener(pluginManager));
            add(new PlayerRespawnListener(playerRoleManager));
        }};
        ListenerManager.getInstance().setPlugin(plugin);
        ListenerManager.getInstance().setPluginManager(pluginManager);
        ListenerManager.getInstance().add(listeners);
    }
}
