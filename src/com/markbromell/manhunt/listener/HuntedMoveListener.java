package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.RoleManager;
import com.markbromell.manhunt.event.PlayerAbsHorizontalMoveEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;
import java.util.function.BiConsumer;

/** Listener for hunted player movement. */
public class HuntedMoveListener implements Listener {
    private final RoleManager playerRoleManager;
    private final PluginManager pluginManager;
    private Location overworldLocation;
    private Location netherLocationFrom;
    private Location netherLocationTo;

    public HuntedMoveListener(RoleManager playerRoleManager, PluginManager pluginManager) {
        this.playerRoleManager = playerRoleManager;
        this.pluginManager = pluginManager;
    }

    /**
     * Calls the absolute horizontal move event for the hunted player.
     * <p>
     * An absolute horizontal move event means that the player moved in the x or z axis by one
     * block.
     *
     * @param event The player move event to calculate the absolute horizontal move from.
     */
    @EventHandler
    public void callAbsHorizontalMoveEvent(PlayerMoveEvent event) {
        if (playerRoleManager.isHunted(event.getPlayer())) {
            assert event.getTo() != null;
            if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                    || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                PlayerAbsHorizontalMoveEvent newMoveEvent = new PlayerAbsHorizontalMoveEvent(
                        event.getPlayer(), event.getFrom(), event.getTo());
                pluginManager.callEvent(newMoveEvent);
            }
        }
    }

    /**
     * Updates the hunters tracking for the hunted player.
     *
     * @param event The event information of the hunted player's movement.
     */
    @EventHandler
    public void updateHunterTracking(PlayerAbsHorizontalMoveEvent event) {
        if (playerRoleManager.isHunted(event.getPlayer())) {
            setLastLocation(event);
            playerRoleManager.getHunters().forEach(this::setCompassTarget);
        }
    }

    /**
     * Sets the last known location of the hunted player.
     *
     * @param event The event triggered by the player moving a block.
     */
    private void setLastLocation(PlayerAbsHorizontalMoveEvent event) {
        if (inOverworld(playerRoleManager.getHunted())) {
            overworldLocation = event.getTo().clone();
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
    private void setCompassTarget(Player player) {
        if (inOverworld(player)) {
            removeLodestoneTracking(player);
            player.setCompassTarget(Objects.requireNonNull(overworldLocation));
        } else if (inNether(player)) {
            setLodestoneTrackingInNether(player);
        }
    }

    /**
     * Checks if a player is in the overworld.
     *
     * @param player The player to check.
     *
     * @return True the player is in the overworld.
     */
    private boolean inOverworld(Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NORMAL;
    }

    /**
     * Checks if a player is in the nether.
     *
     * @param player The player to check.
     *
     * @return True the player is in the nether.
     */
    private boolean inNether(Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NETHER;
    }

    /**
     * Brings the player's compass back to normal, no longer tracking a lodestone.
     *
     * @param player The player that the compass will alter for.
     */
    private void removeLodestoneTracking(Player player) {
        alterCompassMeta(player, (compassMeta, itemStack) -> {
            compassMeta.setLodestone(null);
            compassMeta.setLodestoneTracked(false);
            itemStack.setItemMeta(compassMeta);
        });
    }

    /**
     * Sets the player's compass to point towards a lodestone. This will only occur in the nether.
     * The lodestone will be placed at Y level 0.
     *
     * @param player The player that the compass will alter for.
     */
    private void setLodestoneTrackingInNether(Player player) {
        alterCompassMeta(player, (compassMeta, itemStack) -> {
            netherLocationTo.setY(0);
            player.getWorld().getBlockAt(netherLocationTo).setType(Material.LODESTONE);
            compassMeta.setLodestone(netherLocationTo);
            compassMeta.setLodestoneTracked(true);
            itemStack.setItemMeta(compassMeta);
            netherLocationFrom.setY(0);
            player.getWorld().getBlockAt(netherLocationFrom).setType(Material.BEDROCK);
        });
    }

    /**
     * Wrapper function to alter the compass meta for a player.
     *
     * @param player The player to alter the compass meta for.
     * @param alterFunc The lambda to execute for altering the compass meta.
     */
    private void alterCompassMeta(Player player, BiConsumer<CompassMeta, ItemStack> alterFunc) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == Material.COMPASS) {
                CompassMeta compassMeta = (CompassMeta) itemStack.getItemMeta();
                assert compassMeta != null;
                alterFunc.accept(compassMeta, itemStack);
            }
        }
    }
}
