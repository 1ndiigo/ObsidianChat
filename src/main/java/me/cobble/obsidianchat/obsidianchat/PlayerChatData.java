package me.cobble.obsidianchat.obsidianchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.cobble.obsidianchat.utils.JsonConvertTo;
import me.cobble.obsidianchat.utils.Utils;
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
     * @return Returns a Gson JsonObject of the player's data
     * @see com.google.gson.JsonObject
     */
    public static JsonObject getPlayerChatData(@NotNull UUID playerUUID) {
        File file = ObsidianChat.getPCDFile();
        Gson gson = new Gson();
        JsonReader fr;
        try {
            fr = new JsonReader(new FileReader(file));
            fr.setLenient(true);
            JsonArray jsonArray = gson.fromJson(fr, JsonArray.class);
            JsonObject jsonObject = gson.fromJson(Utils.getValFromJson(jsonArray, playerUUID, JsonConvertTo.JSON_OBJECT), JsonObject.class);

            // wtf is going on
            if (jsonObject == null) {
                try {
                    if (file.createNewFile()) {
                        plugin.getLogger().info("A file was found missing and has been recovered");
                    }
                    addPlayer(playerUUID);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (jsonObject.get(playerUUID.toString()) == null) {
                addPlayer(playerUUID);
            }

            fr.close();

            return jsonObject.get(playerUUID.toString()).getAsJsonObject();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the whole Player Chat Data file as a JsonArray
     *
     * @return The Entire PCD file
     * @see com.google.gson.JsonArray
     */
    public static JsonArray getAllPlayerChatData() {
        File file = ObsidianChat.getPCDFile();
        Gson gson = new Gson();
        JsonReader fr;
        try {
            fr = new JsonReader(new FileReader(file));
            JsonArray jsonObject = gson.fromJson(fr, JsonArray.class);
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
    public static void addPlayer(@NotNull UUID playerUUID) throws IOException {
        File file = ObsidianChat.getPCDFile();
        JsonArray pdc = getAllPlayerChatData();

        if (pdc != null && pdc.get(Integer.parseInt(Utils.getValFromJson(pdc, playerUUID, JsonConvertTo.INT))).getAsJsonObject().has(playerUUID.toString())) {
            return;
        }

        if (getAllPlayerChatData() == null) {
            JsonWriter pw = new JsonWriter(new FileWriter(file, true));

            pw.setLenient(true);

            pw.beginArray().beginObject().name(playerUUID.toString()).beginObject().name("cc").value(Config.get().getString("default-chat-color")).name("tagc").value(Config.get().getString("default-tag-color")).name("nick").value(Bukkit.getPlayer(playerUUID).getName()).endObject().endObject().endArray().close();
        } else {
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new Gson();
            String data = gson.fromJson(new FileReader(file), String.class);
            JsonWriter pw = new JsonWriter(new FileWriter(file, true));

            pw.setLenient(true);

            fileWriter.write(data.replace("]", ","));

            pw.name(playerUUID.toString()).beginObject().name("cc").value(Config.get().getString("default-chat-color")).name("tagc").value(Config.get().getString("default-tag-color")).name("nick").value(Bukkit.getPlayer(playerUUID).getName()).endObject().close();

            fileWriter.append(']');
            fileWriter.close();
        }
    }

    /**
     * Modifies the player's PCD
     *
     * @param playerUUID Player's UUID
     * @param key        JSON key to modify
     * @param value      value to set the JSON key
     */
    public static void modifyPlayerChatData(@NotNull UUID playerUUID, @NotNull String key, @NotNull String value) {
        File file = ObsidianChat.getPCDFile();
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        Gson gson = builder.create();
        JsonObject jsonObject = new JsonObject();
        JsonObject stems = new JsonObject();
        String nickBeforeReset = getPlayerChatData(playerUUID).get("nick").getAsString();
        String tagColorBefore = getPlayerChatData(playerUUID).get("tagc").getAsString();
        String chatColorBefore = getPlayerChatData(playerUUID).get("cc").getAsString();
        PrintWriter pw;
        try {
            ObsidianChat.getPCDFile();

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
