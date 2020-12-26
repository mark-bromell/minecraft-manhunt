package com.markbromell.manhunt.command;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class CommandHunter implements CommandExecutor, TabCompleter {
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String LIST = "list";

    private final Map<String, CommandExecutor> secondaryExecutors;
    private final Server server;

    public CommandHunter(PlayerRoleManager playerRoleManager, Plugin plugin) {
        this.server = plugin.getServer();
        this.secondaryExecutors = new HashMap<String, CommandExecutor>() {{
            put(ADD, new CommandHunterAdd(playerRoleManager, plugin));
            put(REMOVE, new CommandHunterRemove(playerRoleManager, plugin));
            put(LIST, new CommandHunterList(playerRoleManager, plugin));
        }};
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label,
                             String[] args) {
        secondaryExecutors.get(args[0]).onCommand(commandSender, command, args[0],
                Arrays.copyOfRange(args, 1, args.length));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label,
                                      String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add(ADD);
            suggestions.add(REMOVE);
            suggestions.add(LIST);
        }

        if (args.length >= 2 && (args[0].equals(ADD) || args[0].equals(REMOVE))) {
            suggestions.addAll(server.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList()));
        }

        return suggestions;
    }
}
