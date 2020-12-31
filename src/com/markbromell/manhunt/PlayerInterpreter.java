package com.markbromell.manhunt;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerInterpreter {
    private final Server server;

    public PlayerInterpreter(Server server) {
        this.server = server;
    }

    public List<Player> getPlayers(String[] names) {
        return Stream.of(names)
                .map(server::getPlayer)
                .collect(Collectors.toList());
    }

    public List<String> getNames(List<Player> players) {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }

    public List<String> getOnlineNames() {
        return getNames(new ArrayList<>(server.getOnlinePlayers()));
    }

    public String commaSeparateNames(List<Player> players) {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    public List<Player> difference(List<Player> list1, List<Player> list2) {
        return list1.stream()
                .filter(player -> !list2.contains(player))
                .collect(Collectors.toList());
    }
}
