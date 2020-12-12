package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.PlayerRoleManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerRespawnListener implements Listener {
    private final PlayerRoleManager playerRoleManager;

    public PlayerRespawnListener(PlayerRoleManager playerRoleManager) {
        this.playerRoleManager = playerRoleManager;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (playerRoleManager.isHunter(event.getPlayer())) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            event.getPlayer().getWorld().dropItem(event.getRespawnLocation(), compass);
        }
    }
}
