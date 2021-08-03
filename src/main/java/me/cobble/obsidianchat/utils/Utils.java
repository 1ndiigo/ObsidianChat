package me.cobble.obsidianchat.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Utils { // NO_UCD (unused code)
    static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    public static String color(@NotNull String text) {

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++) {
            if (texts[i].equalsIgnoreCase("&")) {
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#') {
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7))).append(texts[i].substring(7));
                } else {
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            } else {
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }

    public static String getValFromJson(JsonArray jsonArray, @NotNull UUID uuid, @NotNull JsonConvertTo jsonConvertTo) throws IllegalStateException {

        if (jsonArray == null) {
            ObsidianChat.initPCD();
        }

        if (jsonConvertTo.equals(JsonConvertTo.JSON_OBJECT)) {
            for (JsonElement jsonElement : jsonArray) {
                if (jsonElement.toString().contains(uuid.toString())) {
                    return jsonElement.getAsJsonObject().toString();
                }
            }
        }

        if (jsonConvertTo.equals(JsonConvertTo.INT)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                Bukkit.broadcastMessage(String.valueOf(jsonArray.get(i).toString().contains(uuid.toString())));
                if (jsonArray.get(i).isJsonObject() && jsonArray.get(i).toString().contains(uuid.toString())) {
                    Bukkit.broadcastMessage("YE HAW");
                    return Integer.toString(i);
                }
            }
            throw new IllegalStateException("no object was found matching that input");
        }

        return null;
    }


}
