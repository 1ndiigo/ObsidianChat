package me.cobble.obsidianchat.obsidianchat;

import me.cobble.obsidianchat.cmds.*;
import me.cobble.obsidianchat.listeners.ObsidianChatListenerMain;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void register(ObsidianChat plugin) {

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
        this.register(plugin);
        new PlayerListRefresh(plugin).run();
        getLogger().info("Registered Components");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // loads config.yml
    private void loadConfig() {
        this.saveDefaultConfig();
        Config.setup();
        Config.get().options().copyDefaults(true);
        Config.get().options().header("Chat Event Prize - This only works if your server has an economy plugin and vault installed (for money prizes, this must end in a $)\nPlayer list Nicknames - Shows your nickname and rank in the player list");
        Config.save();
        initPCD();
    }

    public static File getPCDFile(){
        return file;
    }
}
