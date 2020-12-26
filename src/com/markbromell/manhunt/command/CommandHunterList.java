package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandHunterList implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;
    private final Server server;

    public CommandHunterList(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        String[] messages = playerRoleManager.getHunters().stream()
                .map(Player::getName)
                .toArray(String[]::new);
        commandSender.sendMessage(messages);
        return true;
    }
}
