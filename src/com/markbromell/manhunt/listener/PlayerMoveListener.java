package com.markbromell.manhunt.listener;

import com.markbromell.manhunt.event.PlayerAbsHorizontalMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        updateAbsHorizontalMoveEvent(event);
    }

    private void updateAbsHorizontalMoveEvent(PlayerMoveEvent event) {
        // If there's an absolute horizontal move event.
        if ((event.getFrom().getBlockX() != Objects.requireNonNull(event.getTo()).getBlockX())
                || (event.getFrom().getBlockZ() != Objects.requireNonNull(event.getTo()).getBlockZ())) {
            PlayerAbsHorizontalMoveEvent absHorizontalMoveEvent = new PlayerAbsHorizontalMoveEvent(
                    event.getPlayer(), event.getFrom(), event.getTo());

            Bukkit.getPluginManager().callEvent(absHorizontalMoveEvent);
        }
    }
}
