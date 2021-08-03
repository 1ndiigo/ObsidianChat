package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.obsidianchat.PlayerChatData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public class GenRandomPCDEntries implements CommandExecutor {
    public GenRandomPCDEntries(ObsidianChat plugin) {
        plugin.getCommand("genrandomuuid").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (int i = 0; i < 6; i++) {
            try {
                PlayerChatData.addPlayer(UUID.randomUUID());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
