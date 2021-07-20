package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.obsidianchat.PlayerChatData;
import me.cobble.obsidianchat.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatConfigCmd implements CommandExecutor {
    public ChatConfigCmd(ObsidianChat plugin) {
        plugin.getCommand("chatconfig").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            // Argument Solving
            // Do not question the lack of DRY of this code
            switch (args.length) {
                default:
                    p.sendMessage(ChatUtil.color("&CToo many arguments"));
                case 0:
                    p.sendMessage(ChatUtil.color("&#2d0f3aObsidianChat &7Config"));
                    p.sendMessage(ChatUtil.color("&#2d0f3aUsage: &7/chatconfig (player|plugin)"));
                case 1:
                    if (args[0].equalsIgnoreCase("player")) {
                        p.sendMessage(ChatUtil.color("&cPlease specify a player."));
                    }

                    if (args[0].equalsIgnoreCase("plugin")) {
                        p.sendMessage(ChatUtil.color("&aHI"));
                    }
                case 2:
                    if (args[0].equalsIgnoreCase("player")) {
                        p.sendMessage(ChatUtil.color("&cPlease specify a setting you want to change."));
                    }
                    if (args[0].equalsIgnoreCase("plugin")) {
                        if (args[1].equalsIgnoreCase("reload")) {
                            Config.reload();
                            p.sendMessage(ChatUtil.color("&aPlugin reloaded :D"));
                        }
                    }
                case 3:
                    if (args[0].equalsIgnoreCase("player")) {
                        if (args[2].equalsIgnoreCase("chat-color")) {
                            p.sendMessage(ChatUtil.color("&cPlease specify what color you want."));
                        } else {
                            p.sendMessage(ChatUtil.color("&cInvalid Setting."));
                        }
                    }
                case 4:
                    if (args[0].equalsIgnoreCase("player")) {
                        if (args[2].equalsIgnoreCase("chat-color")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            PlayerChatData.modifyPCD(target.getUniqueId(), args[3], "cc");
                            p.sendMessage(ChatUtil.color("&aSuccessfully set chat color to " + args[3] + "this"));
                        } else if (args[2].equalsIgnoreCase("tag-color")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            PlayerChatData.modifyPCD(target.getUniqueId(), args[3], "tagc");
                            p.sendMessage(ChatUtil.color("&aSuccessfully set tag color to " + args[3] + "this"));
                        }
                    }
            }
        }
        return false;
    }
}
