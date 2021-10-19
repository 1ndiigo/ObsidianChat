package me.cobble.obsidianchat.obsidianchat;

import me.cobble.obsidianchat.cmds.*;
import me.cobble.obsidianchat.listeners.JoinLeaveListeners;
import me.cobble.obsidianchat.listeners.MsgListener;
import me.cobble.obsidianchat.listeners.ObsidianChatListenerMain;
import me.cobble.obsidianchat.utils.Utils;
import me.cobble.obsidianchat.utils.chatdata.ChatDataUtility;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ObsidianChat extends JavaPlugin {


    private static ObsidianChat plugin;

    public static ObsidianChat getPlugin() {
        return plugin;
    }

    private static void register(ObsidianChat plugin) {

        // listeners
        new ObsidianChatListenerMain(plugin);
        new JoinLeaveListeners(plugin);
        new MsgListener(plugin);
        // not actually a command, how sneaky
        new MsgCommand(plugin);

        // commands
        new ChatConfigCmd(plugin);
        new ChatConfigCompleter(plugin);
        new NicknameCmd(plugin);
        new ClearChatCmd(plugin);
        new SocialSpy(plugin);
    }

    @Override
    public void onEnable() {
        plugin = this;

        File file = new File(Bukkit.getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "player-chat-data.json");

        Utils.setPCDFile(file);

        getLogger().info("Loading Config");
        this.loadConfig();
        getLogger().info("Loaded Config");
        getLogger().info("Registering Components");
        ObsidianChat.register(plugin);
        getLogger().info("Registered Components");
        getLogger().info("Starting Tasks");
        new PlayerListRefresh(plugin).run();
        getLogger().info("Tasks Started");
        getLogger().info("Startup Complete");
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving chat data");
        try {
            ChatDataUtility.save();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("Unable to save chat data, some data may be lost");
        }
        getLogger().info("Goodbye!");
    }

    // loads config.yml
    private void loadConfig() {
        this.saveDefaultConfig();
        Config.setup();
        Config.get().options().copyDefaults(true);
        Config.get().options().header("Player list Nicknames - Shows your nickname and rank in the player list");
        Config.save();
        try {
            ChatDataUtility.load();
            ChatDataUtility.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
