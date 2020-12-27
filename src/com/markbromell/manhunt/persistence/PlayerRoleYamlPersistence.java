package com.markbromell.manhunt.persistence;

import com.markbromell.manhunt.PlayerRoleManager;
import com.markbromell.manhunt.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class PlayerRoleYamlPersistence implements RolePersistence {
    private static final String PATH = "plugins/manhunt/roles.yml";
    private final Yaml yaml;
    private final Server server;

    public PlayerRoleYamlPersistence(Yaml yaml, Server server) {
        this.yaml = yaml;
        this.server = server;
    }

    @Override
    public void push(RoleManager roleManager) {
        try {
            FileWriter writer = new FileWriter(PATH);
            Map<String, Object> data = new HashMap<>();
            List<String> hunterNames = roleManager.getHunters().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
            Player hunted = roleManager.getHunted();

            data.put("hunters", hunterNames);
            data.put("hunted", hunted == null ? null : hunted.getName());

            yaml.dump(data, writer);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Unable to persist manhunt role data.");
        }
    }

    @Override
    public RoleManager pull() {
        try {
            Map<String, Object> data = getYamlData();
            List<String> hunterNames = (List<String>) data.get("hunters");
            String huntedName = (String) data.get("hunted");

            Set<Player> hunters = getHuntersFromNames(hunterNames);
            Player hunted = server.getPlayer(huntedName);

            return new PlayerRoleManager(this, hunters, hunted);
        } catch (YAMLException e) {
            Bukkit.getLogger().log(Level.WARNING, "No stored data found for manhunt roles.");
            return new PlayerRoleManager(this);
        }
    }

    private Set<Player> getHuntersFromNames(List<String> hunterNames) {
        Set<Player> hunters = new HashSet<>();

        for (String hunterName : hunterNames) {
            Player hunter = server.getPlayer(hunterName);
            if (hunter != null) {
                hunters.add(hunter);
            }
        }

        return hunters;
    }

    private Map<String, Object> getYamlData() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(PATH);
        return yaml.load(inputStream);
    }
}
