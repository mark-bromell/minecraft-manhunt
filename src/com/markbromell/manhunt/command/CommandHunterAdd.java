package com.markbromell.manhunt.command;

import com.markbromell.manhunt.Manhunt;
import com.markbromell.manhunt.persistence.RoleManager;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Command to add hunters. */
public class CommandHunterAdd implements TabExecutor {
    private final RoleManager roleManager;
    private final Server server;

    public CommandHunterAdd(final RoleManager roleManager, final Server server) {
        this.roleManager = roleManager;
        this.server = server;
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
        List<Player> players = Stream.of(args)
                .map(server::getPlayer)
                .collect(Collectors.toList());

        // Getting the players that were successfully added as a hunter.
        List<Player> added = roleManager.addHunters(players);

        if (added.size() == 0) {
            commandSender.sendMessage(Manhunt.ERROR + "No hunters were added, make sure they " +
                    "are online, and you spell their names correctly");
        }
        else {
            String huntersAdded = added.stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(", "));
            commandSender.sendMessage(Manhunt.INFO + "Hunters added: " + huntersAdded);
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
        if (args.length >= 1) {
            return server.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }

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
