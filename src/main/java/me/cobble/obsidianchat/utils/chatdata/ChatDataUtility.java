package me.cobble.obsidianchat.utils.chatdata;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import me.cobble.obsidianchat.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Various utilities related to chat
 */
public class ChatDataUtility {

    private static final ArrayList<ChatData> allChatData = new ArrayList<>();

    /**
     * Retrieves a player's chat data
     *
     * @param uuid - UUID of player to be retrieved
     * @return The player's ChatData object
     * @see ChatData
     */
    public static ChatData get(UUID uuid) {

        for (ChatData chatDatum : allChatData) {
            if (chatDatum.getUUID().equalsIgnoreCase(uuid.toString())) {
                return chatDatum;
            }
        }
        return null;
    }

    /**
     * Creates entry into ChatData
     *
     * @param uuid      - UUID of player to be created
     * @param nick      - Player's default nick
     * @param chatColor - Default color of chat messages
     */
    public static void create(UUID uuid, String chatColor, String nick) {
        ChatData chatData = new ChatData(uuid, chatColor, nick);
        allChatData.add(chatData);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes player from ChatData
     *
     * @param uuid - UUID of player to remove
     */
    public static void remove(UUID uuid) {
        for (ChatData chatData : allChatData) {
            if (chatData.getUUID().equalsIgnoreCase(uuid.toString())) {
                allChatData.remove(chatData);
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * Saves ChatData changes to file
     *
     * @throws IOException - When the data can not be saved
     */
    public static void save() throws IOException {
        File file = Utils.getPCDFile();
        Gson gson = new Gson();
        Writer writer = new FileWriter(file);

        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();

        gson.toJson(allChatData, writer);

        writer.flush();
        writer.close();
    }

    /**
     * Changes ChatData of player
     *
     * @param uuid    - UUID of player
     * @param newData - Data to change existing ChatData to
     */
    public static void update(UUID uuid, ChatData newData) {
        ChatData chatData = get(uuid);
        if (chatData != null) {
            chatData.setNick(newData.getNick());
            chatData.setChatColor(newData.getChatColor());
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load() throws IOException {
        if (allChatData.isEmpty()) {
            Gson gson = new Gson();
            Reader reader = new FileReader(Utils.getPCDFile());
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            for (JsonElement element : jsonArray) {
                UUID uuid = UUID.fromString(element.getAsJsonObject().get("uuid").getAsString());
                String cc = element.getAsJsonObject().get("chatColor").getAsString();
                String nick = element.getAsJsonObject().get("nick").getAsString();

                create(uuid, cc, nick);
            }
        }
    }
}
