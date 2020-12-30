package com.markbromell.manhunt.command;

import com.markbromell.manhunt.Manhunt;
import com.markbromell.manhunt.persistence.RoleManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Command to remove hunters. */
public class CommandHunterRemove implements TabExecutor {
    private final RoleManager roleManager;
    private final Server server;

    public CommandHunterRemove(final RoleManager roleManager, final Server server) {
        this.roleManager = roleManager;
        this.server = server;
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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String alias, String[] args) {
        List<Player> players = Stream.of(args)
                .map(server::getPlayer)
                .collect(Collectors.toList());

        // Getting the players that were successfully removed as a hunter.
        List<Player> removed = roleManager.removeHunters(players);

        if (removed.size() == 0) {
            commandSender.sendMessage(Manhunt.ERROR + "No hunters were removed, make sure they " +
                    "are online, and you spell their names correctly");
        } else {
            String huntersRemoved = removed.stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(", "));
            commandSender.sendMessage(Manhunt.INFO + "Hunters removed: " + huntersRemoved);
        }
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
    public List<String> onTabComplete(@NotNull CommandSender commandSender,
                                      @NotNull Command command, @NotNull String alias,
                                      String[] args) {
        if (args.length >= 1) {
            return server.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
