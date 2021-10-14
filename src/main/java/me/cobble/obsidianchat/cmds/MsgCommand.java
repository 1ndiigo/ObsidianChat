package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MsgCommand implements Listener {

    public MsgCommand(ObsidianChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onMsgCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/msg") || e.getMessage().startsWith("/minecraft:msg") || e.getMessage().startsWith("/minecraft:w") || e.getMessage().startsWith("/w")) {
            e.setCancelled(true);
            String cmd = e.getMessage();
            String[] args = cmd.split(" ");

            if (args.length > 2) {
                Player receiver = Bukkit.getPlayer(args[1]);
                Player sender = e.getPlayer();
                StringBuilder messageBuilder = new StringBuilder();

                for (int i = 2; i < args.length; i++) {
                    messageBuilder.append(args[i]);
                }

                String message = messageBuilder.toString();

                sender.sendMessage(Utils.color("&e" + sender.getName() + " &7→ &c" + receiver.getName() + "&c: &f" + message));

            } else {
                e.getPlayer().sendMessage(Utils.color("&cPlease enter a target and message"));
            }
        }
    }
}
