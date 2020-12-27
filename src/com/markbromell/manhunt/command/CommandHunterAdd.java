package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Command to add hunters. */
public class CommandHunterAdd implements TabExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Server server;

    public CommandHunterAdd(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
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
    public boolean onCommand(CommandSender commandSender, Command command, String alias,
                             String[] args) {
        List<Player> players = Stream.of(args)
                .map(server::getPlayer)
                .collect(Collectors.toList());
        playerRoleManager.addHunters(players);
        giveCompassToHunters(players);
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

    /**
     * Drop a compass to hunters at their location in their world.
     * @param hunters The hunters to give a compass to.
     */
    private void giveCompassToHunters(List<Player> hunters) {
        for (Player player : hunters) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            player.getWorld().dropItem(player.getLocation(), compass);
        }
    }
}
