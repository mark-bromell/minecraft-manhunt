package com.markbromell.manhunt.persistence;

import com.markbromell.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class PlayerRoleYamlPersistence implements RolePersistence {
    private static final Path ROLES_PATH = Paths.get(Manhunt.BASE_PATH.toString(), "roles.yml");
    private final Yaml yaml;
    private final Server server;

    public PlayerRoleYamlPersistence(Yaml yaml, Server server) {
        this.yaml = yaml;
        this.server = server;
    }

    @Override
    public void push(final RoleManager roleManager) {
        try {
            // Get the data in a workable format.
            List<String> hunterData = roleManager.getPlayers().getHunters().stream()
                    .map(player -> player.getUniqueId().toString())
                    .collect(Collectors.toList());
            Player hunted = roleManager.getPlayers().getHunted();

            // Put the data in the yaml file.
            FileWriter writer = new FileWriter(ROLES_PATH.toString());
            Map<String, Object> data = new HashMap<>();
            data.put("hunters", hunterData);
            data.put("hunted", hunted == null ? null : hunted.getUniqueId().toString());
            yaml.dump(data, writer);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Unable to persist manhunt role data.");
        }
    }

    @Override
    public void pull(final RoleManager roleManager) {
        try {
            // Get the data from the yaml file.
            Map<String, Object> data = getYamlData();
            List<String> huntersUuid = (List<String>) data.get("hunters");
            String huntedUuid = (String) data.get("hunted");

            // Interpolate the data.
            List<Player> hunters = getPlayersFromUuid(huntersUuid);
            Player hunted =
                    huntedUuid == null ? null : server.getPlayer(UUID.fromString(huntedUuid));

            // Update the role manager.
            roleManager.getPlayers().setHunters(hunters);
            roleManager.getPlayers().setHunted(hunted);
        } catch (YAMLException e) {
            Bukkit.getLogger().log(Level.INFO, "No stored data found for manhunt roles.");
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.INFO, "File not found for manhunt roles.");
        }
    }

    /**
     * Gets a list of players from a list of UUIDs. It will only gather players that are online.
     *
     * @param uuidList The UUIDs to get the player objects from.
     *
     * @return Players that are online that are a match with a UUID.
     */
    private List<Player> getPlayersFromUuid(Collection<String> uuidList) {
        List<Player> players = new ArrayList<>();

        for (String uuid : uuidList) {
            Player hunter = server.getPlayer(UUID.fromString(uuid));
            if (hunter != null) {
                players.add(hunter);
            }
        }

        return players;
    }

    /**
     * Generic method to get yaml data from the role file.
     *
     * @return Map of all the yaml file data.
     *
     * @throws FileNotFoundException Cannot find a file that is storing role data.
     */
    private Map<String, Object> getYamlData() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(ROLES_PATH.toFile());
        return yaml.load(inputStream);
    }
}
