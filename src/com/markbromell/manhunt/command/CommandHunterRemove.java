package com.markbromell.manhunt.command;

import com.markbromell.manhunt.RoleManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Command to remove hunters. */
public class CommandHunterRemove implements TabExecutor {
    private final RoleManager playerRoleManager;
    private final Server server;

    public CommandHunterRemove(RoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
    }

    /**
     * Executes when the command is submitted.
     * <p>
     * Removes players as a hunter.
     *
     * @param commandSender The sender of the command.
     * @param command The command in context.
     * @param alias The alias of the command.
     * @param args The args of the command (appear after the alias).
     *
     * @return Success or failure of the command execution.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias,
                             String[] args) {
        Set<Player> players = Stream.of(args)
                .map(server::getPlayer)
                .collect(Collectors.toSet());
        playerRoleManager.removeHunters(players);
        return true;
    }

    /**
     * Shows arg suggestions, showing a list of all the online users.
     *
     * @param commandSender The sender of the command.
     * @param command The command in context.
     * @param alias The alias of the command.
     * @param args The args of the command (appear after the alias).
     *
     * @return Each online player name.
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias,
                                      String[] args) {
        if (args.length >= 1) {
            return server.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
