package me.cobble.obsidianchat.obsidianchat;

import com.google.gson.stream.JsonWriter;
import me.cobble.obsidianchat.cmds.ChatConfigCmd;
import me.cobble.obsidianchat.cmds.ChatConfigCompleter;
import me.cobble.obsidianchat.cmds.ClearChatCmd;
import me.cobble.obsidianchat.cmds.NicknameCmd;
import me.cobble.obsidianchat.listeners.ObsidianChatListenerMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class ObsidianChat extends JavaPlugin {

    private static ObsidianChat plugin;
    private static File file;

    public static ObsidianChat getPlugin() {
        return plugin;
    }

    public static void initPCD() {
        if (!file.exists()) {
            try {
                plugin.getLogger().info("Creating player data");
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();

                if (Bukkit.getOnlinePlayers().size() > 0) {
                    JsonWriter pw = new JsonWriter(new FileWriter(file));

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        pw.beginArray().beginObject().name(p.getUniqueId().toString()).beginObject().name("cc").value(Config.get().getString("default-chat-color")).name("tagc").value(Config.get().getString("default-tag-color")).name("nick").value(Bukkit.getPlayer(p.getUniqueId()).getName()).endObject().endObject().endArray().close();
                    }
                }

            } catch (IOException ignored) {
                // help
            }
        }
    }

    private static void register(ObsidianChat plugin) {

        // listeners
        new ObsidianChatListenerMain(plugin);

        // commands
        new ChatConfigCmd(plugin);
        new ChatConfigCompleter(plugin);
        new NicknameCmd(plugin);
        new ClearChatCmd(plugin);
    }

    @Override
    public void onEnable() {
        plugin = this;

        file = new File(Bukkit.getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "player-chat-data.json");

        new PlayerChatData(plugin);

        this.loadConfig();
        getLogger().info("Loaded Config");
        ObsidianChat.register(plugin);
        new PlayerListRefresh(plugin).run();
        getLogger().info("Registered Components");
    }

    // loads config.yml
    private void loadConfig() {
        this.saveDefaultConfig();
        Config.setup();
        Config.get().options().copyDefaults(true);
        Config.get().options().header("Player list Nicknames - Shows your nickname and rank in the player list");
        Config.save();
        initPCD();
    }

    public static File getPCDFile() {
        return file;
    }
}
