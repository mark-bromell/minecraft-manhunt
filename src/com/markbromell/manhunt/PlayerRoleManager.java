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

    public PlayerRoleManager(Player hunted, Set<Player> hunters) {
        this.hunted = hunted;
        this.hunters = hunters;
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

    public void setHunters(Collection<Player> players) {
        hunters.addAll(players);
    }

    public void addHunter(Player player) {
        hunters.add(player);
    }

    public void removeHunter(Player player) {
        hunters.remove(player);
    }

    public boolean isHunter(Player player) {
        return hunters.contains(player);
    }
}
