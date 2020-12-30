package com.markbromell.manhunt.persistence;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The purpose of this role manager is to coordinate the control over PlayerRoles. Each method may
 * perform operations of persistence and events, whereas the PlayerRoles is simply a class to
 * contain data.
 */
public interface RoleManager {
    /**
     * Gets the collection of the player roles that are being managed.
     *
     * @return The collection of player roles that are being managed.
     */
    PlayerRoles getPlayers();

    /**
     * Sets the hunted player.
     * <p>
     * There is possible confusion with the <code>getPlayers().setHunted(...)</code>. This is the
     * method that should be used in the context of the manhunt game. Only use the
     * <code>getPlayers().setHunted(...)</code> method if you know what you are doing.
     *
     * @param hunted The player to set as the hunted player.
     *
     * @return {@code true} if the player was set as the hunted.
     */
    boolean setHunted(@NotNull Player hunted);

    /**
     * Adds a hunter to the list of hunters.
     *
     * @param hunter The player to add as a hunter.
     *
     * @return {@code true} if the player was added as a hunter.
     */
    boolean addHunter(@NotNull Player hunter);

    /**
     * Adds a collection of hunters to the list of hunters.
     *
     * @param hunters The players to add as hunters.
     *
     * @return The collection of players that were successfully added as a hunter.
     */
    List<Player> addHunters(List<Player> hunters);

    /**
     * Removes a hunter from the list of hunters.
     *
     * @param hunter The player to remove from the hunter list.
     *
     * @return {@code true} if the player was removed as a hunter.
     */
    boolean removeHunter(@NotNull Player hunter);

    /**
     * Removes a collection of hunters from the hunters list.
     *
     * @param hunters The players to remove from the hunters list.
     *
     * @return The collection of players that were successfully removed from the hunters list.
     */
    List<Player> removeHunters(List<Player> hunters);

    /**
     * Checks to see if a player is a hunter.
     *
     * @param player The player to check.
     *
     * @return {@code true} if the player is a hunter.
     */
    boolean isHunter(Player player);

    /**
     * Checks to see if a player is the hunted.
     *
     * @param player The player to check.
     *
     * @return {@code true} if the player is a hunter.
     */
    boolean isHunted(Player player);
}
