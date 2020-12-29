package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.persistence.RoleManager;
import com.markbromell.manhunt.persistence.RolePersistence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final RolePersistence rolePersistence;
    private final RoleManager roleManager;

    public PlayerJoinListener(final RolePersistence rolePersistence,
                              final RoleManager roleManager) {
        this.rolePersistence = rolePersistence;
        this.roleManager = roleManager;
    }

    /**
     * Reset the role manager to sync with the newly joined player.
     *
     * @param event Player join event information
     */
    @EventHandler
    public void pullPersistedRoleData(PlayerJoinEvent event) {
        rolePersistence.pull(roleManager);
    }
}
