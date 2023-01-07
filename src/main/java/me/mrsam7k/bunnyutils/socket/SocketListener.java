package me.mrsam7k.bunnyutils.socket;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.mrsam7k.bunnyutils.Bunnyutils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.util.Signer;

import java.net.http.WebSocket;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class SocketListener implements WebSocket.Listener {

    @Override
    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        Bunnyutils.LOGGER.info("Received message from server: " + data.toString());
        JsonObject json = (JsonObject) JsonParser.parseString(data.toString());
        if (json.has("type")) {
            String type = json.get("type").getAsString();
            switch (type) {
                case "ping":
                    Bunnyutils.LOGGER.info("Received ping from socket server, responding");
                    webSocket.sendText("{\"type\": \"ping\"}", true);
                    break;
                case "connected_players":
                    JsonArray players = json.get("players").getAsJsonArray();
                    Bunnyutils.PLAYERS_WITH_MOD.clear();
                    for (JsonElement player : players) {
                        Bunnyutils.PLAYERS_WITH_MOD.add(UUID.fromString(player.getAsString()));
                    }
                    break;
                default:
                    Bunnyutils.LOGGER.warn("Received packet of unknown type from socket server: " + type);
                    break;
            }
        } else Bunnyutils.LOGGER.warn("Unknown packet received");
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        Bunnyutils.LOGGER.warn("(" + statusCode + ") Socket connection closed for reason: " + reason);
        SocketHandler.connected = false;
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

}
