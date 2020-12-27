package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.RoleManager;
import com.markbromell.manhunt.persistence.RolePersistence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final RolePersistence persistence;
    private RoleManager roleManager;

    public PlayerJoinListener(RolePersistence persistence, RoleManager roleManager) {
        this.persistence = persistence;
        this.roleManager = roleManager;
    }

    @EventHandler
    public void queryPersistedData(PlayerJoinEvent event) {
        // TODO: This does not replace the original role manager.
        roleManager = persistence.pull();
    }
}
