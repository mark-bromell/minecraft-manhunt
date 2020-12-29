package com.markbromell.manhunt.command;

import com.markbromell.manhunt.persistence.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/** Command to display the list of hunters */
public class CommandHunterList extends TerminalCommand implements TabExecutor {
    private final RoleManager roleManager;

    public CommandHunterList(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /**
     * Executes when the command is submitted.
     * <p>
     * Displays the list of current hunters. If there are no hunters, it will notify that instead.
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
        String heading = ChatColor.DARK_PURPLE + "[Hunters]";
        List<String> messages = roleManager.getPlayers().getHunters().stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        if (messages.size() > 0) {
            messages.add(0, heading);
            commandSender.sendMessage(messages.toArray(new String[0]));
        } else {
            String message = ChatColor.YELLOW + "There are no hunters";
            commandSender.sendMessage(message);
        }
        return true;
    }
}
