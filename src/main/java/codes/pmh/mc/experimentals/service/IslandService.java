package codes.pmh.mc.experimentals.service;

import codes.pmh.mc.experimentals.datatype.Island;
import codes.pmh.mc.experimentals.datatype.IslandBuilder;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class IslandService {
    private final JavaPlugin plugin;
    private final Server server;
    private final FileConfiguration config;
    private final Random random = new Random();

    public IslandService (JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.server = plugin.getServer();
    }

    public List<Island> listIslands () {
        String rawWorld = this.config.getString("island.world");
        double yAxis = this.config.getDouble("island.y");

        assert rawWorld != null;
        @NotNull List<Map<?, ?>> rawIslands = this.config.getMapList("island.islands");
        World world = this.server.getWorld(rawWorld);

        List<Island> islands = new ArrayList<>();

        for (Map<?, ?> rawIsland : rawIslands) {
            double xAxis = (double) (int) rawIsland.get("x");
            double zAxis = (double) (int) rawIsland.get("z");

            Island island = new IslandBuilder()
                    .setName((String) rawIsland.get("name"))
                    .setOwner(server.getPlayer(UUID.fromString(rawIsland.get("owner").toString())))
                    .setLocation(new Location(world, xAxis, yAxis, zAxis))
                    .build();

            islands.add(island);
        }

        return islands;
    }

    public Island findIsland (String name) {
        String rawWorld = this.config.getString("island.world");
        double yAxis = this.config.getDouble("island.y");

        assert rawWorld != null;
        @NotNull List<Map<?, ?>> rawIslands = this.config.getMapList("island.islands");
        World world = this.server.getWorld(rawWorld);

        Map<?, ?> rawIsland = rawIslands
                .stream()
                .filter((island) -> island.get("name").equals(name))
                .findFirst()
                .orElse(null);

        if (rawIsland == null)
            return null;

        double xAxis = (double) (int) rawIsland.get("x");
        double zAxis = (double) (int) rawIsland.get("z");

        return new IslandBuilder()
                .setName((String) rawIsland.get("name"))
                .setOwner(server.getPlayer(UUID.fromString(rawIsland.get("owner").toString())))
                .setLocation(new Location(world, xAxis, yAxis, zAxis))
                .build();
    }

    public Island createIsland (String name, Player owner) {
        String rawWorld = this.config.getString("island.world");
        int yAxis = this.config.getInt("island.y");

        assert rawWorld != null;
        World world = this.server.getWorld(rawWorld);

        int xAxis = (int) (random.nextDouble() * 20000) - 10000;
        int zAxis = (int) (random.nextDouble() * 20000) - 10000;

        assert world != null;
        world.setBlockData(xAxis, yAxis - 1, zAxis, server.createBlockData(Material.GLASS));

        return new IslandBuilder()
                .setName(name)
                .setOwner(owner)
                .setLocation(new Location(world, xAxis, yAxis, zAxis))
                .build();
    }

    public Island createIsland (String name, Player owner, int xAxis, int zAxis) {
        String rawWorld = this.config.getString("island.world");
        int yAxis = this.config.getInt("island.y");

        assert rawWorld != null;
        World world = this.server.getWorld(rawWorld);

        return new IslandBuilder()
                .setName(name)
                .setOwner(owner)
                .setLocation(new Location(world, xAxis, yAxis, zAxis))
                .build();
    }

    public void deleteIsland (Island island) {
        @NotNull List<Map<?, ?>> originalIslands = this.config.getMapList("island.islands");
        List<Map<?, ?>> filteredIslands = originalIslands
                .stream()
                .filter((i) -> !i.get("name").equals(island.getName()))
                .toList();

        this.config.set("island.islands", filteredIslands);
        this.plugin.saveConfig();
    }

    public void saveIsland (Island island) {
        @NotNull List<Map<?, ?>> rawIslands = this.config.getMapList("island.islands");
        Map<String, Object> rawIsland = new HashMap<>();

        rawIsland.put("name", island.getName());
        rawIsland.put("owner", island.getOwner().getUniqueId().toString());
        rawIsland.put("x", island.getLocation().getBlockX());
        rawIsland.put("z", island.getLocation().getBlockZ());

        rawIslands.add(rawIsland);

        this.config.set("island.islands", rawIslands);
        this.plugin.saveConfig();
    }
}
