package codes.pmh.mc.experimentals.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerRegister {
    private final JavaPlugin plugin;
    private final PluginManager pluginManager;

    public ListenerRegister(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginManager = plugin.getServer().getPluginManager();
    }

    public ListenerRegister registListener(Listener listener) {
        this.pluginManager.registerEvents(listener, this.plugin);
        return this;
    }
}
