package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatConfigCompleter implements TabCompleter {

    public ChatConfigCompleter(ObsidianChat plugin) {
        plugin.getCommand("chatconfig").setTabCompleter(this);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> selector = new ArrayList<>();

            if (sender.hasPermission("obsidianchat.configure.player")) {
                selector.add("player");
            }

            if (sender.hasPermission("obsidianchat.configure.plugin")) {
                selector.add("plugin");
            }
            return selector;
        }

        if (args[0].equalsIgnoreCase("player")) {
            if (args.length == 3) {
                ArrayList<String> options = new ArrayList<>();
                options.add("chat-color");

                return options;
            }

            ArrayList<String> players = new ArrayList<>();

            if (sender.hasPermission("obsidianchat.configure.player.others")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    players.add(p.getName());
                }
            }
            return players;
        }

        if (args[0].equalsIgnoreCase("plugin")) {
            ArrayList<String> plOptions = new ArrayList<>();
            plOptions.add("default-chat-color");
            plOptions.add("reload");

            return plOptions;
        }
        return null;
    }
}
