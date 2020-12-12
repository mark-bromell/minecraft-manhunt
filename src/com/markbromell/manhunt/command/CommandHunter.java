package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandHunter implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;

    public CommandHunter(PlayerRoleManager playerRoleManager) {
        this.playerRoleManager = playerRoleManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        List<Player> players = Stream.of(args).map(Bukkit.getServer()::getPlayer).collect(Collectors.toList());
        playerRoleManager.setHunters(players);
        giveCompassToHunters();
        return true;
    }

    private void giveCompassToHunters() {
        for (Player player : playerRoleManager.getHunters()) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            player.getWorld().dropItem(player.getLocation(), compass);
        }
    }
}
