package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.event.PlayerAbsHorizontalMoveEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerMoveListener implements Listener {
    private final PluginManager pluginManager;

    public PlayerMoveListener(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        updateAbsHorizontalMoveEvent(event);
    }

    private void updateAbsHorizontalMoveEvent(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        assert to != null;

        // If there's an absolute horizontal move event.
        if (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()) {
            PlayerAbsHorizontalMoveEvent absHorizontalMoveEvent =
                    new PlayerAbsHorizontalMoveEvent(event.getPlayer(), from, to);
            pluginManager.callEvent(absHorizontalMoveEvent);
        }
    }
}
