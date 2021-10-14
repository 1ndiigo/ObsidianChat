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

public class NicknameCmd implements CommandExecutor {
    public NicknameCmd(ObsidianChat plugin) {
        plugin.getCommand("nickname").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("obsidianchat.nick")) {
                if (args.length == 0) {
                    p.sendMessage(Utils.color(Config.get().getString("nickname-no-params-msg")));
                }

                if (args.length == 1) {
                    ChatData chatData = ChatDataUtility.get(p.getUniqueId());

                    if (chatData == null) {
                        ChatDataUtility.create(p.getUniqueId(), Config.get().getString("default-chat-color"), args[0]);
                        return false;
                    }

                    chatData.setChatColor(chatData.getChatColor());
                    chatData.setNick(args[0]);
                    ChatDataUtility.update(p.getUniqueId(), chatData);
                    p.sendMessage(Utils.color("&aSuccessfully set nickname"));
                }

                if (args.length == 2) {
                    if (p.hasPermission("obisidianchat.nick.others")) {
                        Player t = Bukkit.getPlayer(args[1]);

                        if (t == null) {
                            p.sendMessage(Utils.color("&cThat player is not online or does not exist"));
                            return false;
                        }

                        ChatData chatData = ChatDataUtility.get(t.getUniqueId());

                        if (chatData == null) {
                            ChatDataUtility.create(t.getUniqueId(), Config.get().getString("default-chat-color"), args[0]);
                            return false;
                        }

                        chatData.setChatColor(chatData.getChatColor());
                        chatData.setNick(args[0]);
                        ChatDataUtility.update(t.getUniqueId(), chatData);
                        p.sendMessage(Utils.color("&aSuccessfully set nickname"));
                    } else {
                        p.sendMessage(Utils.color("&aYou do not have permission to change other's nicknames"));
                    }
                }
                if (args.length > 2) {
                    p.sendMessage(Utils.color("&cToo many args"));
                }
            }
        }
        return false;
    }
}
