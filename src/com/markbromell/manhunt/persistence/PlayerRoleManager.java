package com.markbromell.manhunt.persistence;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerRoleManager implements RoleManager {
    private final RolePersistence persistence;
    private final PlayerRoles players;

    public PlayerRoleManager(final RolePersistence persistence) {
        this.persistence = persistence;
        this.players = new PlayerRoles();
        this.persistence.pull(this);
    }

    @Override
    public PlayerRoles getPlayers() {
        return players;
    }

    @Override
    public boolean setHunted(@NotNull Player hunted) {
        players.setHunted(hunted);
        persistence.push(this);
        return true;
    }

    @Override
    public boolean addHunter(@NotNull Player hunter) {
        if (huntersContains(hunter)) {
            return false;
        }
        players.getHunters().add(hunter);
        persistence.push(this);
        return true;
    }

    private boolean huntersContains(Player subject) {
        return players.getHunters().stream().anyMatch(playerEquality(subject));
    }

    private Predicate<Player> playerEquality(Player subject) {
        return player -> subject.getUniqueId().equals(player.getUniqueId());
    }

    @Override
    public List<Player> addHunters(List<Player> hunters) {
        hunters = removeNull(hunters);
        for (Player hunter : hunters) {
            if (!huntersContains(hunter)) {
                players.getHunters().add(hunter);
            }
        }
        persistence.push(this);
        return hunters;
    }

    @Override
    public boolean removeHunter(@NotNull Player hunter) {
        boolean removed = players.getHunters().removeIf(playerEquality(hunter));
        persistence.push(this);
        return removed;
    }

    @Override
    public List<Player> removeHunters(List<Player> hunters) {
        hunters = removeNull(hunters);
        List<Player> removed = new ArrayList<>();

        for (Player hunter : hunters) {
            if (players.getHunters().removeIf(playerEquality(hunter))) {
                removed.add(hunter);
            }
        }
        persistence.push(this);
        return removed;
    }

    @Override
    public boolean isHunter(Player subject) {
        return players.getHunters().stream().anyMatch(playerEquality(subject));
    }

    @Override
    public boolean isHunted(Player subject) {
        if (players.getHunted() == null) {
            return false;
        }
        return playerEquality(subject).test(players.getHunted());
    }

    private List<Player> removeNull(Collection<Player> players) {
        return players.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
