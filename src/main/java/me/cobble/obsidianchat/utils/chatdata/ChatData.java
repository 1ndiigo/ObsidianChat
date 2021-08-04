package me.cobble.obsidianchat.utils.chatdata;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatData {

    private final String uuid;
    private final Player playerObj;
    private String chatColor;
    private String nick;
    private final JsonObject jsonObject = new JsonObject();

    public ChatData(UUID uuid) {
        this.uuid = uuid.toString();
        this.playerObj = Bukkit.getPlayer(uuid);

        this.jsonObject.addProperty("nick", getNick());
        this.jsonObject.addProperty("cc", getChatColor());
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

    public Player getPlayerObject() {
        return playerObj;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}
