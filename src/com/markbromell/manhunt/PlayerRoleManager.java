package com.markbromell.manhunt;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PlayerRoleManager {
    private final Set<Player> hunters;
    private Player hunted;

    public PlayerRoleManager() {
        this.hunted = null;
        this.hunters = new HashSet<>();
    }

    public Player getHunted() {
        return hunted;
    }

    public void setHunted(Player hunted) {
        this.hunted = hunted;
    }

    public Set<Player> getHunters() {
        return hunters;
    }

    public void addHunter(Player player) {
        hunters.add(player);
    }

    public void addHunters(Collection<Player> players) {
        hunters.addAll(players);
    }

    public void removeHunter(Player player) {
        hunters.remove(player);
    }

    public void removeHunters(Collection<Player> players) {
        hunters.removeAll(players);
    }

    public boolean isHunter(Player player) {
        return hunters.contains(player);
    }

    public boolean isHunted(Player player) {
        return player.equals(hunted);
    }
}
