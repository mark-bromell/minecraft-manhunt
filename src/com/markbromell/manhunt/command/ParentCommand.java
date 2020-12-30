package com.markbromell.manhunt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Parent of other commands, so this class wont really execute anything, it is just a way to easily
 * have nested child commands.
 */
public class ParentCommand implements TabExecutor {
    protected Map<String, TabExecutor> childExecutors;

    public ParentCommand(Map<String, TabExecutor> childExecutors) {
        this.childExecutors = childExecutors;
    }

    /**
     * Executes when the command is submitted.
     * <p>
     * It will dispatch to the corresponding child command executor based on the first arg.
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
        String childAlias = args[0];
        String[] childArgs = Arrays.copyOfRange(args, 1, args.length);
        childExecutors.get(childAlias).onCommand(commandSender, command, childAlias, childArgs);
        return true;
    }

    /**
     * Suggests inputs for arguments.
     * <p>
     * It will gather suggestions for corresponding child executors.
     *
     * @param commandSender The sender of the command.
     * @param command The command in context.
     * @param alias The alias of the command.
     * @param args The args of the command (appear after the alias).
     *
     * @return Success or failure of the command execution.
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender,
                                      @NotNull Command command, @NotNull String alias,
                                      String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(childExecutors.keySet());
        }

        String childAlias = args[0];
        String[] childArgs = Arrays.copyOfRange(args, 1, args.length);
        return childExecutors.get(childAlias)
                .onTabComplete(commandSender, command, childAlias, childArgs);
    }
}
