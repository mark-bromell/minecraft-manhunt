package com.markbromell.manhunt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/** A terminal (final) command in a chain of commands. */
public class TerminalCommand implements TabCompleter {
    /** The suggested args for the command (there are none). */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender,
                                      @NotNull Command command, @NotNull String alias,
                                      String[] args) {
        return new ArrayList<>();
    }
}
