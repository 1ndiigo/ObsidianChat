package me.cobble.obsidianchat.cmds;

import me.cobble.obsidianchat.obsidianchat.Config;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import me.cobble.obsidianchat.obsidianchat.PlayerChatData;
import me.cobble.obsidianchat.utils.Utils;
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
                    PlayerChatData.modifyPlayerChatData(p.getUniqueId(), Utils.color(args[0]), "nick");
                    p.setDisplayName(Utils.color(args[0]));
                    p.sendMessage(Utils.color("&aSuccessfully set nickname"));
                }

                if (args.length == 2) {
                    Player t = Bukkit.getPlayer(args[0]);
                    PlayerChatData.modifyPlayerChatData(t.getUniqueId(), Utils.color(args[0]), "nick");
                    p.sendMessage(Utils.color("&aSuccessfully set nickname"));
                }
                if (args.length > 2) {
                    p.sendMessage(Utils.color("&cToo many args"));
                }
            }
        }
        return false;
    }
}
