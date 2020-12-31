package com.markbromell.manhunt.command;

import com.beust.jcommander.Strings;
import com.markbromell.manhunt.Manhunt;
import com.markbromell.manhunt.PlayerInterpreter;
import com.markbromell.manhunt.persistence.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Command to add hunters. */
public class CommandHunterAdd implements TabExecutor {
    private final RoleManager roleManager;
    private final PlayerInterpreter playerInterpreter;

    public CommandHunterAdd(RoleManager roleManager, PlayerInterpreter playerInterpreter) {
        this.roleManager = roleManager;
        this.playerInterpreter = playerInterpreter;
    }

    /**
     * Executes when the command is submitted.
     * <p>
     * Adds players as hunters.
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

        // Getting the players that were successfully added as a hunter, and failed.
        List<Player> added = roleManager.addHunters(players);
        List<String> failed = playerInterpreter.difference(
                Arrays.asList(args), playerInterpreter.getNames(added));

        if (added.size() == 0) {
            commandSender.sendMessage(Manhunt.ERROR + "No hunters were added, make sure they " +
                    "are online, and you spell their names correctly");
        } else {
            String addedString = playerInterpreter.commaSeparateNames(added);
            commandSender.sendMessage(Manhunt.INFO + "Hunters added: " + addedString);
        }

        if (failed.size() > 0) {
            String failedString = String.join(", ", failed);
            commandSender.sendMessage(Manhunt.ERROR + "Hunters failed to add: " + failedString);
        }

        giveCompassToHunters(added);
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

    /**
     * Drop a compass to hunters at their location in their world.
     *
     * @param hunters The hunters to give a compass to.
     */
    private void giveCompassToHunters(List<Player> hunters) {
        for (Player player : hunters) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            player.getWorld().dropItem(player.getLocation(), compass);
        }
    }
}
