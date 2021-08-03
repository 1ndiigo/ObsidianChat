package me.cobble.obsidianchat.listeners;

import com.google.gson.JsonObject;
import me.clip.placeholderapi.PlaceholderAPI;
import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.obsidianchat.PlayerChatData;
import me.cobble.obsidianchat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class ObsidianChatListenerMain implements Listener {
    public ObsidianChatListenerMain(ObsidianChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        JsonObject pcd = PlayerChatData.getPlayerChatData(p.getUniqueId());

        if (pcd == null) {
            if (ObsidianChat.getPCDFile().exists()) {
                try {
                    PlayerChatData.addPlayer(p.getUniqueId());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                ObsidianChat.initPCD();
            }
        }

        String c = pcd.get("cc").getAsString();

        if (!Config.get().getBoolean("message-format-in-yml")) {
            if (PlaceholderAPI.containsPlaceholders(msg)) {
                e.setCancelled(true);
                p.sendMessage(Utils.color("&cDo not send placeholders"));
            }

            e.setFormat(Utils.color(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%" + " &r&8| &r&7" + PlayerChatData.getPlayerChatData(p.getUniqueId()).get("nick").getAsString() + " &r&8» ") + c + msg.replace('%', ' ')));
        } else {
            if (PlaceholderAPI.containsPlaceholders(msg)) {
                e.setCancelled(true);
                p.sendMessage(Utils.color("&cDo not send placeholders"));
            }
            e.setFormat(Utils.color(PlaceholderAPI.setPlaceholders(p, Config.get().getString("message-format")) + c + msg.replace('%', ' ')));
        }

        if (Config.get().getBoolean("message-send-sound")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.25F, 1);
            }
        }

    }
}
