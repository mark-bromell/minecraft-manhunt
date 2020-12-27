package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

/** Listens for when a hunter respawns */
public class HunterRespawnListener implements Listener {
    private final PlayerRoleManager playerRoleManager;

    public HunterRespawnListener(PlayerRoleManager playerRoleManager) {
        this.playerRoleManager = playerRoleManager;
    }

    /**
     * Drops a compass at the hunters exact location in their world after they respawn.
     *
     * @param event The event information for the respawned player.
     */
    @EventHandler
    public void giveCompassToHunter(PlayerRespawnEvent event) {
        if (playerRoleManager.isHunter(event.getPlayer())) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            event.getPlayer().getWorld().dropItem(event.getRespawnLocation(), compass);
        }
    }
}
