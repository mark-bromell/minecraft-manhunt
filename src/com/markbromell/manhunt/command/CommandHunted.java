package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.listener.PlayerAbsHorizontalMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandHunted implements CommandExecutor {
    private final PlayerRoleManager playerRoleManager;

    public CommandHunted(PlayerRoleManager playerRoleManager) {
        this.playerRoleManager = playerRoleManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player hunted = Bukkit.getServer().getPlayer(args[0]);
        playerRoleManager.setHunted(hunted);
        Bukkit.getPluginManager().registerEvents(
                new PlayerAbsHorizontalMoveListener(playerRoleManager),
                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Manhunt"))
        );
        return true;
    }
}
