package com.markbromell.manhunt;

import com.markbromell.manhunt.persistence.RolePersistence;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PlayerRoleManager implements RoleManager {
    private final Set<Player> hunters;
    private final RolePersistence persistence;
    private Player hunted;

    public PlayerRoleManager(RolePersistence persistence) {
        this.hunters = new HashSet<>();
        this.persistence = persistence;
        this.hunted = null;
    }

    @Override
    public Player getHunted() {
        return hunted;
    }

    @Override
    public void setHunted(Player hunted) {
        this.hunted = hunted;
        persistence.push(this);
    }

    @Override
    public Set<Player> getHunters() {
        return hunters;
    }

    @Override
    public void addHunter(Player hunter) {
        hunters.add(hunter);
        persistence.push(this);
    }

    @Override
    public void addHunters(Collection<Player> hunters) {
        this.hunters.addAll(hunters);
        persistence.push(this);
    }

    @Override
    public void removeHunter(Player hunter) {
        hunters.remove(hunter);
        persistence.push(this);
    }

    @Override
    public void removeHunters(Collection<Player> hunters) {
        this.hunters.removeAll(hunters);
        persistence.push(this);
    }

    @Override
    public boolean isHunter(Player player) {
        return hunters.contains(player);
    }

    @Override
    public boolean isHunted(Player player) {
        return player.equals(hunted);
    }
}
