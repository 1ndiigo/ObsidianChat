package me.cobble.obsidianchat.utils;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Utils { // NO_UCD (unused code)
    static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
    private static File file;

    /**
     * Applies color codes to imported test, including RGB
     *
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
                    finalText.append(ChatColor.of(texts[i].substring(0, 7))).append(texts[i].substring(7));
                } else {
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            } else {
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }

    public static File getPCDFile() {
        return file;
    }

    public static void setPCDFile(File file) {
        Utils.file = file;
    }
}
