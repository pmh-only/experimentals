package codes.pmh.mc.experimentals.service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OutputService {
    private final Server server = Bukkit.getServer();

    public void sendCommandOutput (CommandSender sender, String message) {
        sender.sendMessage(Component.text(message));

        if (!(sender instanceof Player))
            return;

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.isOp() || player.equals(sender))
                continue;

            player.sendMessage(Component.text(
                    "[" + sender.getName() + ": " + message + "]",
                    Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC)
            ));
        }
    }
}
