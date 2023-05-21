package codes.pmh.mc.experimentals.datatype;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IslandBuilder {
    private String name;
    private Player owner;
    private Location location;

    public IslandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public IslandBuilder setOwner(Player owner) {
        this.owner = owner;
        return this;
    }

    public IslandBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Island build() {
        return new Island(name, owner, location);
    }
}