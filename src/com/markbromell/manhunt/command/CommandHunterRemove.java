package com.markbromell.manhunt.command;

import com.markbromell.manhunt.Manhunt;
import com.markbromell.manhunt.PlayerInterpreter;
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
    private final PlayerInterpreter playerInterpreter;

    public CommandHunterRemove(RoleManager roleManager, PlayerInterpreter playerInterpreter) {
        this.roleManager = roleManager;
        this.playerInterpreter = playerInterpreter;
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
        List<Player> players = playerInterpreter.getPlayers(args);

        // Getting the players that were successfully removed as a hunter, and failed.
        List<Player> removed = roleManager.removeHunters(players);
        List<Player> failed = playerInterpreter.difference(players, removed);

        if (removed.size() == 0) {
            commandSender.sendMessage(Manhunt.ERROR + "No hunters were removed, make sure they " +
                    "are online, and you spell their names correctly");
        } else {
            String removedString = playerInterpreter.commaSeparateNames(removed);
            commandSender.sendMessage(Manhunt.INFO + "Hunters removed: " + removedString);
        }

        if (failed.size() > 0) {
            String failedString = playerInterpreter.commaSeparateNames(failed);
            commandSender.sendMessage(Manhunt.ERROR + "Hunters failed to remove: " + failedString);
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
        if (args.length >= 1) return playerInterpreter.getOnlineNames();
        return new ArrayList<>();
    }
}
