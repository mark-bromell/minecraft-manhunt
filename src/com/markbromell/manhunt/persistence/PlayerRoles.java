package com.markbromell.manhunt.persistence;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRoles {
    private List<Player> hunters;
    private Player hunted;

    public PlayerRoles() {
        this.hunters = new ArrayList<>();
        this.hunted = null;
    }

    public List<Player> getHunters() {
        return hunters;
    }

    public void setHunters(List<Player> hunters) {
        this.hunters = hunters;
    }

    public Player getHunted() {
        return hunted;
    }

    public void setHunted(Player hunted) {
        this.hunted = hunted;
    }
}
