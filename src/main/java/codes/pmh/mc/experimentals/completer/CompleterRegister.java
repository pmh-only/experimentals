package codes.pmh.mc.experimentals.completer;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class CompleterRegister {
    private final JavaPlugin plugin;

    public CompleterRegister(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public CompleterRegister registCompleter (String commandName, TabCompleter completer) {
        PluginCommand command = this.plugin.getCommand(commandName);

        if (command != null)
            command.setTabCompleter(completer);

        return this;
    }
}
