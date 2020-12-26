package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandHunterAdd implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Server server;

    public CommandHunterAdd(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        List<Player> players = Stream.of(args)
                .map(server::getPlayer)
                .collect(Collectors.toList());
        playerRoleManager.addHunters(players);
        giveCompassToHunters(players);
        return true;
    }

    private void giveCompassToHunters(List<Player> players) {
        for (Player player : players) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            player.getWorld().dropItem(player.getLocation(), compass);
        }
    }
}
