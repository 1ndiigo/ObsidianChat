package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class QueryUsername implements CommandExecutor {

    public QueryUsername(ObsidianChat plugin) {
        plugin.getCommand("queryusername").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            p.sendMessage(p.getDisplayName());
        }
        return false;
    }
}
