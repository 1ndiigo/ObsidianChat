package me.cobble.obsidianchat.listeners;

import me.cobble.obsidianchat.cmds.SocialSpy;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class MsgListener implements Listener {

    private static ObsidianChat plugin;

    public MsgListener(ObsidianChat plugin) {
        MsgListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onMsg(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/msg") || e.getMessage().startsWith("/minecraft:msg") || e.getMessage().startsWith("/minecraft:w") || e.getMessage().startsWith("/w")) {
            String[] args = e.getMessage().split(" ");

            Player sender = e.getPlayer();
            Player receiver = Bukkit.getPlayer(args[1]);

            StringBuilder message = new StringBuilder();
            ArrayList<Player> snoopers = SocialSpy.snooping;

            for (String s : Arrays.copyOfRange(args, 2, args.length)) {
                message.append(s);
            }

            for (Player p : snoopers) {
                p.sendMessage(Utils.color("&l&cSpy &r&7| &e" + sender.getName() + " &7→ &c" + receiver.getName() + ": &7" + message));
                if (!sender.equals(p) && !receiver.equals(p)) {
                    p.sendMessage(Utils.color("&l&cSpy &r&7| &e" + sender.getName() + " &7→ &c" + receiver.getName() + ": &7" + message));
                }
            }
        }
    }
}
