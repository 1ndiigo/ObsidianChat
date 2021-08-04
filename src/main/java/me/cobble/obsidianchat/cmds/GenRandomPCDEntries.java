package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.chatdata.ChatDataUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GenRandomPCDEntries implements CommandExecutor {
    public GenRandomPCDEntries(ObsidianChat plugin) {
        plugin.getCommand("genrandomuuid").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int num = args.length == 1 ? Integer.parseInt(args[0]) : 6;
        for (int i = 0; i < num; i++) {
            ChatDataUtility.createPlayerChatData(UUID.randomUUID(), "randomNick", "&7");
        }
        return false;
    }
}
