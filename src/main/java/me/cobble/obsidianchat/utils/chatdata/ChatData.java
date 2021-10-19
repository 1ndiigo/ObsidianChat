package me.cobble.obsidianchat.utils.chatdata;

import java.util.UUID;

/**
 * ChatData class
 */
public class ChatData {

    private final String uuid;
    private String chatColor;
    private String nick;

    /**
     * Container class for storing data related to chat
     *
     * @param uuid - uuid of player
     * @param chatColor - chat color of player
     * @param nick - nickname of player
     */
    public ChatData(UUID uuid, String chatColor, String nick) {
        this.uuid = uuid.toString();
        this.chatColor = chatColor;
        this.nick = nick;
    }

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUUID() {
        return uuid;
    }
}
