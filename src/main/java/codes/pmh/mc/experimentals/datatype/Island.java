package codes.pmh.mc.experimentals.datatype;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Island {
    private final String name;

    private final Player owner;

    private final Location location;

    public Island(String name, Player owner, Location location) {
        this.name = name;
        this.owner = owner;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }
}
