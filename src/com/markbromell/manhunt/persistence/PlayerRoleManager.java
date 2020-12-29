package com.markbromell.manhunt.persistence;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Predicate;

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
    public void setHunted(Player hunted) {
        players.setHunted(hunted);
        persistence.push(this);
    }

    @Override
    public void addHunter(Player hunter) {
        if (!huntersContains(hunter)) {
            players.getHunters().add(hunter);
            persistence.push(this);
        }
    }

    private boolean huntersContains(Player subject) {
        return players.getHunters().stream()
                .anyMatch(playerEquality(subject));
    }

    private Predicate<Player> playerEquality(Player subject) {
        return player -> subject.getUniqueId().equals(player.getUniqueId());
    }

    @Override
    public void addHunters(Collection<Player> hunters) {
        for (Player hunter : hunters) {
            if (!huntersContains(hunter)) {
                players.getHunters().add(hunter);
            }
        }
        persistence.push(this);
    }

    @Override
    public void removeHunter(Player hunter) {
        players.getHunters().removeIf(playerEquality(hunter));
        persistence.push(this);
    }

    @Override
    public void removeHunters(Collection<Player> hunters) {
        hunters.forEach(this::removeHunter);
        persistence.push(this);
    }

    @Override
    public boolean isHunter(Player subject) {
        return players.getHunters().stream()
                .anyMatch(playerEquality(subject));
    }

    @Override
    public boolean isHunted(Player subject) {
        if (players.getHunted() == null) {
            return false;
        }
        return playerEquality(subject).test(players.getHunted());
    }
}
