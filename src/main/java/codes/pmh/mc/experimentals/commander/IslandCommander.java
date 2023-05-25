package codes.pmh.mc.experimentals.commander;

import codes.pmh.mc.experimentals.datatype.Island;
import codes.pmh.mc.experimentals.service.IslandService;
import codes.pmh.mc.experimentals.service.OutputService;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandCommander implements CommandExecutor {
    private final IslandService islandService;
    private final OutputService outputService;

    public IslandCommander(IslandService islandService, OutputService outputService) {
        this.islandService = islandService;
        this.outputService = outputService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2)
            return false;

        if (!(sender instanceof Player player)) {
            outputService.sendCommandOutput(sender, "Command /island must run by player.");
            return true;
        }

        if (args[0].equals("create")) {
            Island foundIsland = islandService.findIsland(args[1]);

            if (foundIsland != null) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " already exists.");
                return true;
            }

            Island island = islandService.createIsland(args[1], player);
            islandService.saveIsland(island);

            outputService.sendCommandOutput(sender,
                    "Island " + island.getName() +
                            " has been created on: X " + island.getLocation().getBlockX() +
                            ", Z " + island.getLocation().getBlockZ());

            return true;
        }

        if (args[0].equals("delete")) {
            Island island = islandService.findIsland(args[1]);

            if (island == null) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " does not exist.");
                return true;
            }

            if (!island.getOwner().equals(player) && !player.isOp()) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " does not owned by " + player.getName());
                return true;
            }

            islandService.deleteIsland(island);
            outputService.sendCommandOutput(sender,
                    "Island " + island.getName() + " has been deleted.");

            return true;
        }

        if (args[0].equals("go")) {
            Island island = islandService.findIsland(args[1]);

            if (island == null) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " not exists.");
                return true;
            }

            player.teleport(island.getLocation().add(0.5, 0.5, 0.5));

            outputService.sendCommandOutput(sender,
                    "Teleport " + sender.getName() + " to island: " + island.getName());

            return true;
        }

        if (args[0].equals("sethome")) {
            Island island = islandService.findIsland(args[1]);

            if (island == null) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " does not exist.");
                return true;
            }

            if (!island.getOwner().equals(player) && !player.isOp()) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " does not owned by " + player.getName());
                return true;
            }

            Location playerLocation = player.getLocation().add(0.5, 0.5, 0.5);

            islandService.deleteIsland(island);
            Island newIsland = islandService.createIsland(island.getName(), player, playerLocation.getBlockX(), playerLocation.getBlockZ());
            islandService.saveIsland(newIsland);

            outputService.sendCommandOutput(sender,
                    "Island " + island.getName() +
                            " has been moved to: X " + newIsland.getLocation().getBlockX() +
                            ", Z " + newIsland.getLocation().getBlockZ());

            return true;
        }

        if (args[0].equals("info")) {
            Island island = islandService.findIsland(args[1]);

            if (island == null) {
                outputService.sendCommandOutput(sender, "Island " + args[1] + " does not exist.");
                return true;
            }

            outputService.sendCommandOutput(sender,
                    "Island " + island.getName() + "\n" +
                    "--------\n" +
                    "Owner: " + island.getOwner().getName() + "\n" +
                    "Location: X " + island.getLocation().getBlockX() +
                            " Z " + island.getLocation().getBlockZ());
            return true;
        }

        return false;
    }
}
