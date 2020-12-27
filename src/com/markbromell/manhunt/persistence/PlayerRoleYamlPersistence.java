package com.markbromell.manhunt.persistence;

import com.markbromell.manhunt.RoleManager;
import org.bukkit.entity.Player;

import java.util.Set;

public class PlayerRoleYamlPersistence implements RolePersistence {
    private Set<Player> hunters;
    private Player hunted;

    @Override
    public void push(RoleManager roleManager) {
        this.hunters = roleManager.getHunters();
        this.hunted = roleManager.getHunted();


    }

    @Override
    public RoleManager pull() {
        return null;
    }
}
