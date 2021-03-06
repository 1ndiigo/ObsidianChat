package me.cobble.obsidianchat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import me.cobble.obsidianchat.utils.chatdata.ChatData;
import me.cobble.obsidianchat.utils.chatdata.ChatDataUtility;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ObsidianChatListenerMain implements Listener {
    public ObsidianChatListenerMain(ObsidianChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        String msg = e.getMessage();
        ChatData pcd = ChatDataUtility.get(p.getUniqueId());

        if (pcd == null) {
            ChatDataUtility.create(p.getUniqueId(), Config.get().getString("default-chat-color"), p.getName());
        }

        String c = pcd.getChatColor();

        if (!Config.get().getBoolean("message-format-in-yml")) {
            if (PlaceholderAPI.containsPlaceholders(msg)) {
                e.setCancelled(true);
                p.sendMessage(Utils.color("&cDo not send placeholders"));
            }
            if (p.hasPermission("obisidianchat.chat.color")) {
                e.setFormat(Utils.color(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%" + " &r&8| &r&7" + pcd.getNick() + " &r&8» ") + c + msg.replace('%', ' ')));
            } else {
                e.setFormat(Utils.color(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%" + " &r&8| &r&7" + pcd.getNick() + " &r&8» ") + c) + msg.replace('%', ' '));
            }
        } else {
            if (PlaceholderAPI.containsPlaceholders(msg)) {
                e.setCancelled(true);
                p.sendMessage(Utils.color("&cDo not send placeholders"));
            }
            if (p.hasPermission("obisidianchat.chat.color")) {
                e.setFormat(Utils.color(PlaceholderAPI.setPlaceholders(p, Config.get().getString("message-format")) + c + msg.replace('%', ' ')));
            } else {
                e.setFormat(Utils.color(PlaceholderAPI.setPlaceholders(p, Config.get().getString("message-format")) + c) + msg.replace('%', ' '));
            }
        }

        if (Config.get().getBoolean("message-send-sound")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.25F, 1);
            }
        }

    }
}
