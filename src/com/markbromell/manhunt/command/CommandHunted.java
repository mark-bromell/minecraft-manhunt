package com.markbromell.manhunt.command;

import com.markbromell.manhunt.persistence.RoleManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Command to set the hunted player. */
public class CommandHunted implements TabExecutor {
    private final RoleManager playerRoleManager;
    private final Server server;

    public CommandHunted(final RoleManager playerRoleManager, final Server server) {
        this.playerRoleManager = playerRoleManager;
        this.server = server;
    }

    /**
     * Executes when the command is submitted.
     * <p>
     * Sets the hunted player.
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
        Player hunted = server.getPlayer(args[0]);
        playerRoleManager.setHunted(hunted);
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
