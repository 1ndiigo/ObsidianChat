package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SocialSpy implements CommandExecutor {

    public static ArrayList<Player> snooping = new ArrayList<>();

    public SocialSpy(ObsidianChat plugin) {
        plugin.getCommand("socialspy").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("obsidianchat.spy") && sender instanceof Player) {
            Player p = (Player) sender;

            if (snooping.contains(p)) {
                p.sendMessage(Utils.color("&7You are no longer spying on private messages"));
                snooping.remove(p);
            } else {
                p.sendMessage(Utils.color("&aYou are now spying on private messages"));
                snooping.add(p);
            }

        } else {
            sender.sendMessage(Utils.color("&cYou do not have permission to run this command"));
        }
        return false;
    }
}
