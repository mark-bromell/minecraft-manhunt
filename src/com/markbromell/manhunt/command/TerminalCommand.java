package com.markbromell.manhunt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

/** A terminal (final) command in a chain of commands. */
public class TerminalCommand implements TabCompleter {
    /** The suggested args for the command (there are none). */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias,
                                      String[] args) {
        return new ArrayList<>();
    }
}
