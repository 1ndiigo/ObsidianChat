package me.cobble.obsidianchat.listeners;

import com.google.gson.JsonObject;
import me.clip.placeholderapi.PlaceholderAPI;
import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.obsidianchat.PlayerChatData;
import me.cobble.obsidianchat.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ObsidianChatListenerMain implements Listener {
    public ObsidianChatListenerMain(ObsidianChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        JsonObject pcd = PlayerChatData.getPCD(p.getUniqueId());

        if (pcd == null) {
            if(ObsidianChat.getPCDFile().exists()) {
                PlayerChatData.createPCD(p.getUniqueId());
            } else {
                ObsidianChat.initPCD();
            }
        }

        String c = pcd.get("cc").getAsString();

        if (!Config.get().getBoolean("message-format-in-yml")) {
            if (PlaceholderAPI.containsPlaceholders(msg)) {
                e.setCancelled(true);
                p.sendMessage(ChatUtil.color("&cDo not send placeholders"));
            }

            e.setFormat(ChatUtil.color(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%" + " &r&8| &r&7" + PlayerChatData.getPCD(p.getUniqueId()).get("nick").getAsString() + " &r&8» ") + c + msg.replace('%', ' ')));
        } else {
            if (PlaceholderAPI.containsPlaceholders(msg)) {
                e.setCancelled(true);
                p.sendMessage(ChatUtil.color("&cDo not send placeholders"));
            }
            e.setFormat(ChatUtil.color(PlaceholderAPI.setPlaceholders(p, Config.get().getString("message-format")) + c + msg.replace('%', ' ')));
        }

        if (Config.get().getBoolean("message-send-sound")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.25F, 1);
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (PlayerChatData.getEntirePCD() == null || !PlayerChatData.getEntirePCD().has(p.getUniqueId().toString()) || PlayerChatData.getPCD(p.getUniqueId()) == null) {
            if(ObsidianChat.getPCDFile().exists()) {
                PlayerChatData.createPCD(p.getUniqueId());
            } else {
                ObsidianChat.initPCD();
            }
        } else {
            if (Config.get().getBoolean("playerlist-modification")) {
                if (Config.get().getBoolean("playerlist-names")) {
                    p.setPlayerListName(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix% &8| &7%player_displayname%"));
                }
                StringBuilder headerString = new StringBuilder();
                StringBuilder footerString = new StringBuilder();

                for(String str : Config.get().getStringList("player-list-header")){
                    headerString.append(str).append("\n");
                }

                for(String str : Config.get().getStringList("player-list-footer")){
                    footerString.append(str).append("\n");
                }

                p.setPlayerListHeader(PlaceholderAPI.setPlaceholders(p, ChatUtil.color(headerString.toString())));
                p.setPlayerListFooter(PlaceholderAPI.setPlaceholders(p, ChatUtil.color(footerString.toString())));
            }
        }
    }
}
