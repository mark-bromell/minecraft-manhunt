package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.RoleManager;
import jdk.nashorn.internal.ir.Terminal;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

/** Command to set the hunted player. */
public class CommandHunted extends TerminalCommand implements TabExecutor {
    private final RoleManager playerRoleManager;
    private final Server server;

    public CommandHunted(RoleManager playerRoleManager, Plugin plugin) {
        this.playerRoleManager = playerRoleManager;
        this.server = plugin.getServer();
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
}
