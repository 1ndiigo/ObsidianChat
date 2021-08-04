package me.cobble.obsidianchat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import me.cobble.obsidianchat.utils.chatdata.ChatDataUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListeners implements Listener {
    public JoinLeaveListeners(ObsidianChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPlayedBefore() || ChatDataUtility.getPlayerChatData(p.getUniqueId()) == null) {
            ChatDataUtility.createPlayerChatData(p.getUniqueId(), p.getDisplayName(), "&7");
        }

        if (Config.get().getBoolean("playerlist-modification")) {
            if (Config.get().getBoolean("playerlist-names")) {
                p.setPlayerListName(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix% &8| &7%player_displayname%"));
            }
            StringBuilder headerString = new StringBuilder();
            StringBuilder footerString = new StringBuilder();

            for (String str : Config.get().getStringList("player-list-header")) {
                headerString.append(str).append("\n");
            }

            for (String str : Config.get().getStringList("player-list-footer")) {
                footerString.append(str).append("\n");
            }

            p.setPlayerListHeader(PlaceholderAPI.setPlaceholders(p, Utils.color(headerString.toString())));
            p.setPlayerListFooter(PlaceholderAPI.setPlaceholders(p, Utils.color(footerString.toString())));
        }
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        p.setPlayerListHeader("");
        p.setPlayerListFooter("");
    }
}
