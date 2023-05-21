package codes.pmh.mc.experimentals;

import codes.pmh.mc.experimentals.commander.CommanderRegister;
import codes.pmh.mc.experimentals.commander.IslandCommander;
import codes.pmh.mc.experimentals.completer.CompleterRegister;
import codes.pmh.mc.experimentals.completer.IslandCommandCompleter;
import codes.pmh.mc.experimentals.listener.ListenerRegister;
import codes.pmh.mc.experimentals.service.IslandService;
import codes.pmh.mc.experimentals.service.OutputService;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMain extends JavaPlugin {
    private final ListenerRegister listenerRegister = new ListenerRegister(this);

    private final CommanderRegister commanderRegister = new CommanderRegister(this);

    private final CompleterRegister completerRegister = new CompleterRegister(this);

    private final IslandService islandService = new IslandService(this);

    private final OutputService outputService = new OutputService();

    @Override
    public void onEnable() {
        this.completerRegister
                .registCompleter("island", new IslandCommandCompleter(this.islandService));

        this.commanderRegister
                .registCommander("island", new IslandCommander(islandService, outputService));

        this.saveDefaultConfig();
    }
}
