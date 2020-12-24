package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.event.PlayerAbsHorizontalMoveEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.Objects;

/** Listener for Player movement so that compasses can track the target Player. */
public class PlayerAbsHorizontalMoveListener implements Listener {
    private final PlayerRoleManager playerRoleManager;

    // Last known locations for each world, makes sure the compass doesn't point to a location in
    // the Players world while the target is in another world. Prevents false location tracking.
    private Location overWorldLocation;
    private Location netherLocationFrom;
    private Location netherLocationTo;

    public PlayerAbsHorizontalMoveListener(PlayerRoleManager playerRoleManager) {
        this.playerRoleManager = playerRoleManager;
    }

    @EventHandler
    public void onPlayerAbsoluteHorizontalMove(PlayerAbsHorizontalMoveEvent event) {
        if (event.getPlayer().equals(playerRoleManager.getHunted())) {
            setLocation(event);

            for (Player player : playerRoleManager.getHunters()) {
                trackLocation(player);
            }
        }
    }

    /** Sets the last known location for the target Player for each world. */
    private void setLocation(PlayerAbsHorizontalMoveEvent event) {
        if (inOverWorld(playerRoleManager.getHunted())) {
            overWorldLocation = event.getTo().clone();
        } else if (inNether(playerRoleManager.getHunted())) {
            netherLocationFrom = event.getFrom().clone();
            netherLocationTo = event.getTo().clone();
        }
    }

    /**
     * Tracks the last known location of the target Player in the current Player's world.
     *
     * @param player The Player that the compass target will be set on.
     */
    private void trackLocation(Player player) {
        if (inOverWorld(player)) {
            removeLodestonePoint(player);
            player.setCompassTarget(Objects.requireNonNull(overWorldLocation));
        } else if (inNether(player)) {
            setLodestonePoint(player);
        }
    }

    private boolean inOverWorld(Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NORMAL;
    }

    private boolean inNether(Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NETHER;
    }

    private void removeLodestonePoint(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            // Get a compass item stack.
            if (itemStack != null && itemStack.getType() == Material.COMPASS) {
                CompassMeta meta = (CompassMeta) itemStack.getItemMeta();
                assert meta != null;

                // Remove the lodestone from the compass meta so that it can work in the overworld.
                meta.setLodestone(null);
                meta.setLodestoneTracked(false);
                itemStack.setItemMeta(meta);

                // This break means that only the first compass item stack encountered will be
                // modified.
                break;
            }
        }
    }

    private void setLodestonePoint(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            // Get a compass item stack.
            if (itemStack != null && itemStack.getType() == Material.COMPASS) {
                CompassMeta meta = (CompassMeta) itemStack.getItemMeta();
                assert meta != null;

                // Place a lodestone at y level 0 and set the compass to point to that.
                netherLocationTo.setY(0);
                player.getWorld().getBlockAt(netherLocationTo).setType(Material.LODESTONE);
                meta.setLodestone(netherLocationTo);
                meta.setLodestoneTracked(true);
                itemStack.setItemMeta(meta);

                // Replace the location 'from' back to bedrock.
                netherLocationFrom.setY(0);
                player.getWorld().getBlockAt(netherLocationFrom).setType(Material.BEDROCK);

                // This break means that only the first compass item stack encountered will
                // be modified.
                break;
            }
        }
    }
}
