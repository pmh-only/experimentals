package codes.pmh.mc.experimentals.completer;

import codes.pmh.mc.experimentals.datatype.Island;
import codes.pmh.mc.experimentals.service.IslandService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class IslandCommandCompleter implements TabCompleter {
    private final IslandService islandService;

    public IslandCommandCompleter(IslandService islandService) {
        this.islandService = islandService;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2)
            return Arrays.asList("create", "delete", "go", "sethome", "info");

        if (args.length < 3 && !args[0].equals("create"))
            return this.islandService.listIslands()
                    .stream()
                    .map(Island::getName)
                    .filter((name) -> name.startsWith(args[1]))
                    .toList();

        return List.of();
    }
}
