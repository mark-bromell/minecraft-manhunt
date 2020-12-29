package com.markbromell.manhunt.persistence;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface RoleManager {
    PlayerRoles getPlayers();

    void setHunted(Player hunted);

    void addHunter(Player hunter);

    void addHunters(Collection<Player> hunters);

    void removeHunter(Player hunter);

    void removeHunters(Collection<Player> hunters);

    boolean isHunter(Player player);

    boolean isHunted(Player player);
}
