package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.utils.Utils;
import me.cobble.obsidianchat.utils.chatdata.ChatData;
import me.cobble.obsidianchat.utils.chatdata.ChatDataUtility;
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
                    p.sendMessage(Utils.color("&CToo many arguments"));
                    break;
                case 0:
                    p.sendMessage(Utils.color("&#2d0f3aObsidianChat &7Config"));
                    p.sendMessage(Utils.color("&#2d0f3aUsage: &7/chatconfig (player|plugin)"));
                    break;
                case 1:
                    if (args[0].equalsIgnoreCase("player")) {
                        p.sendMessage(Utils.color("&cPlease specify a player."));
                        break;
                    }

                    if (args[0].equalsIgnoreCase("plugin")) {
                        p.sendMessage(Utils.color("&aHI"));
                        break;
                    }
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("player")) {
                        p.sendMessage(Utils.color("&cPlease specify a setting you want to change."));
                        break;
                    }
                    if (args[0].equalsIgnoreCase("plugin")) {
                        if (args[1].equalsIgnoreCase("reload")) {
                            Config.reload();
                            ObsidianChat.initPCD();
                            p.sendMessage(Utils.color("&aPlugin reloaded :D"));
                            break;
                        }
                    }
                    break;
                case 3:
                    if (args[0].equalsIgnoreCase("player")) {
                        if (args[2].equalsIgnoreCase("chat-color")) {
                            p.sendMessage(Utils.color("&cPlease specify what color you want."));
                        } else {
                            p.sendMessage(Utils.color("&cInvalid Setting."));
                        }
                        break;
                    }
                    break;
                case 4:
                    if (args[0].equalsIgnoreCase("player")) {
                        if (args[2].equalsIgnoreCase("chat-color")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            ChatData chatData = ChatDataUtility.getPlayerChatData(target.getUniqueId());
                            chatData.setNick(args[3]);
                            ChatDataUtility.updateChatData(target.getUniqueId(), chatData);
                            p.sendMessage(Utils.color("&aSuccessfully set chat color to " + args[3] + "this"));
                            break;
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
