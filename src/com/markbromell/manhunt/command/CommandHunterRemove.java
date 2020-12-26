package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandHunterRemove implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Server server;

    public CommandHunterRemove(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        Set<Player> players = Stream.of(args)
                .map(server::getPlayer)
                .collect(Collectors.toSet());
        playerRoleManager.removeHunters(players);
        return true;
    }
}
