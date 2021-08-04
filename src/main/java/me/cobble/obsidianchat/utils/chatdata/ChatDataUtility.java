package me.cobble.obsidianchat.utils.chatdata;

import com.google.gson.Gson;
import me.cobble.obsidianchat.obsidianchat.ObsidianChat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Various utilities related to chat
 */
public class ChatDataUtility {

    private static final ArrayList<ChatData> allChatData = new ArrayList<>();
    private static ObsidianChat plugin;

    public ChatDataUtility(ObsidianChat plugin) {
        ChatDataUtility.plugin = plugin;
    }

    public static ChatData getPlayerChatData(UUID uuid) {
        for (ChatData allChatDatum : ChatDataUtility.allChatData) {
            if (allChatDatum.getUUID().equalsIgnoreCase(uuid.toString())) {
                return allChatDatum;
            }
        }
        return null;
    }

    public static void createPlayerChatData(UUID uuid, String nick, String chatColor) {
        ChatData chatData = new ChatData(uuid);
        chatData.setChatColor(chatColor);
        chatData.setNick(nick);
        allChatData.add(chatData);
        try {
            saveChatData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeChatData(UUID uuid) {
        for (ChatData chatData : allChatData) {
            if (chatData.getUUID().equalsIgnoreCase(uuid.toString())) {
                allChatData.remove(chatData);
                try {
                    saveChatData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void saveChatData() throws IOException {
        Gson gson = new Gson();
        File file = ObsidianChat.getPCDFile();
        Logger log = plugin.getLogger();

        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(allChatData, writer);
        writer.flush();
        writer.close();
        log.info("Chat related data successfully saved!");
    }

    public static ChatData updateChatData(UUID uuid, ChatData newData) {
        for (ChatData allChatDatum : allChatData) {
            if (allChatDatum.getUUID().equalsIgnoreCase(uuid.toString())) {
                allChatDatum.setNick(newData.getNick());
                allChatDatum.setChatColor(newData.getChatColor());
                try {
                    saveChatData();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return allChatDatum;
            }
        }
        return null;
    }

    public static void loadChatData() {

    }
}
