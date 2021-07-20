package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChatCmd implements CommandExecutor {
    public ClearChatCmd(ObsidianChat plugin) {
        plugin.getCommand("clearchat").setExecutor(this);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("obisidianchat.clearchat")) {
            for (int i = 0; i < 150; i++) {
                Bukkit.broadcastMessage(" ");
            }

            sender.sendMessage(Utils.chat("&aChat Cleared!"));
            if (Config.get().getBoolean("broadcast-who-cleared-chat")) {
                if (sender instanceof ConsoleCommandSender) {
                    Bukkit.broadcastMessage(Utils.chat("&aChat Cleared by Console"));
                }
                if (sender instanceof Player) {
                    Bukkit.broadcastMessage(Utils.chat("&aChat Cleared by " + sender.getName()));
                }
            }
        }
        return false;
    }
}
