package com.markbromell.manhunt;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;

public interface RoleManager {
    Player getHunted();

    void setHunted(Player hunted);

    Set<Player> getHunters();

    void setHunters(Set<Player> hunters);

    void addHunter(Player hunter);

    void addHunters(Collection<Player> hunters);

    void removeHunter(Player hunter);

    void removeHunters(Collection<Player> hunters);

    boolean isHunter(Player player);

    boolean isHunted(Player player);
}
