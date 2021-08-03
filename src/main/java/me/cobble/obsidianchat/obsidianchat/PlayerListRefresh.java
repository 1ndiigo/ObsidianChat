package me.cobble.obsidianchat.obsidianchat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cobble.obsidianchat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerListRefresh {

    private final ObsidianChat plugin;

    public PlayerListRefresh(ObsidianChat plugin) {
        this.plugin = plugin;
    }

    public void run() {

        if (Config.get().getBoolean("playerlist-modification")) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Config.get().getBoolean("playerlist-names")) {
                        p.setPlayerListName(PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix% &8| &7%player_displayname%"));
                    } else {
                        p.setPlayerListName(p.getDisplayName());
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
            }, 0L, 20L * 3);
        }
    }

}
