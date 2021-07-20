package me.cobble.obsidianchat.obsidianchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.UUID;

/**
 * Various utilities related to chat
 */
public class PlayerChatData {

    private static ObsidianChat plugin;

    public PlayerChatData(ObsidianChat plugin) {
        PlayerChatData.plugin = plugin;
    }

    /**
     * Retrieves Player Chat Data from file
     *
     * @param playerUUID The player's UUID
     * @returns Returns a Gson JsonObject of the player's data
     * @see com.google.gson.JsonObject
     */
    public static JsonObject getPCD(@NotNull UUID playerUUID) {
        File file = new File(Bukkit.getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "player-chat-data.json");
        Gson gson = new Gson();
        JsonReader fr;
        try {
            fr = new JsonReader(new FileReader(file));
            fr.setLenient(true);
            JsonObject jsonObject = gson.fromJson(fr, JsonObject.class);

            // wtf is going on
            if (jsonObject == null) {
                try {
                    if (file.createNewFile()) {
                        plugin.getLogger().info("A file was found missing and has been recovered");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (jsonObject.get(playerUUID.toString()) == null) {
                createPCD(playerUUID);
            }

            fr.close();

            return jsonObject.get(playerUUID.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Gets the whole Player Chat Data file as a JsonObject
     *
     * @see com.google.gson.JsonObject
     * @returns The Entire PCD file
     */
    public static JsonObject getEntirePCD() {
        File file = ObsidianChat.getPCDFile();
        Gson gson = new Gson();
        JsonReader fr;
        Reader fileReader;
        try {
            fileReader = new FileReader(file);
            fr = new JsonReader(fileReader);
            fr.setLenient(true);

            JsonObject jsonObject = gson.fromJson(fr, JsonObject.class);
            fr.close();
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Creates an entry in the PCD file
     *
     * @param playerUUID Player's UUID
     * @see org.bukkit.entity.Player
     */
    public static void createPCD(UUID playerUUID) {
        File file = ObsidianChat.getPCDFile();
        GsonBuilder builder = new GsonBuilder();

        builder.setLenient();
        Gson gson = builder.create();
        JsonObject jsonObject = new JsonObject();
        JsonObject stems = new JsonObject();

        jsonObject.addProperty("cc", Config.get().getString("default-chat-color"));
        jsonObject.addProperty("tagc", Config.get().getString("default-tag-color"));
        jsonObject.addProperty("nick", Bukkit.getPlayer(playerUUID).getName());
        stems.add(playerUUID.toString(), jsonObject);

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));

            pw.write(gson.toJson(stems));
            pw.write(',');
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifies the player's PCD
     *
     * @param playerUUID Player's UUID
     * @param key JSON key to modify
     * @param value value to set the JSON key
     */
    public static void modifyPCD(UUID playerUUID, String key, String value) {
        File file = ObsidianChat.getPCDFile();
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        Gson gson = builder.create();
        JsonObject jsonObject = new JsonObject();
        JsonObject stems = new JsonObject();
        String nickBeforeReset = getPCD(playerUUID).get("nick").getAsString();
        String tagColorBefore = getPCD(playerUUID).get("tagc").getAsString();
        String chatColorBefore = getPCD(playerUUID).get("cc").getAsString();
        PrintWriter pw;
        try {
            PlayerChatData.getEntirePCD().remove(playerUUID.toString());

            if (value.equalsIgnoreCase("cc")) {
                jsonObject.addProperty("cc", key);
                jsonObject.addProperty("tagc", tagColorBefore);
                jsonObject.addProperty("nick", nickBeforeReset);
            } else if (value.equalsIgnoreCase("nick")) {
                jsonObject.addProperty("tagc", tagColorBefore);
                jsonObject.addProperty("cc", chatColorBefore);
                jsonObject.addProperty("nick", key);
            } else {
                jsonObject.addProperty("tagc", key);
                jsonObject.addProperty("cc", chatColorBefore);
                jsonObject.addProperty("nick", nickBeforeReset);
            }
            stems.add(playerUUID.toString(), jsonObject);

            pw = new PrintWriter(new FileWriter(file));

            pw.write(gson.toJson(stems));
            pw.write(',');
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
