package codes.pmh.mc.experimentals.commander;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommanderRegister {
    private final JavaPlugin plugin;

    public CommanderRegister(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public CommanderRegister registCommander(String commandName, CommandExecutor executor) {
        PluginCommand command = this.plugin.getCommand(commandName);

        if (command != null)
            command.setExecutor(executor);

        return this;
    }
}
