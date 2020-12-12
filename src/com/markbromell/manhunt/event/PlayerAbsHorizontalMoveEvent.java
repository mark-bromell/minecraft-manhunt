package com.markbromell.manhunt.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Holds information for when the player moves horizontally from one block to another.
 * 'Absolute' meaning that the absolute value of the x or z coordinate has changed value.
 */
public class PlayerAbsHorizontalMoveEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Location from;
    private final Location to;

    public PlayerAbsHorizontalMoveEvent(Player player, Location from, Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Gets the player that moved.
     * @return The player that moved.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the location the player moved from.
     * @return The location the player moved from.
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Gets the location the player moved to.
     * @return The location the player moved to.
     */
    public Location getTo() {
        return to;
    }
}
