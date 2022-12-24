package me.mrsam7k.bunnyutils.socket;

import com.google.gson.JsonObject;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.ExecutionException;

public class SocketHandler {

    public static boolean connected = false;
    public static WebSocket socket;

    public SocketHandler() {
        if (connected) return;
        connected = true;
        Bunnyutils.LOGGER.info("Initialising socket connection to server...");

        HttpClient client = HttpClient.newHttpClient();
        try {
            socket = client.newWebSocketBuilder()
                    .buildAsync(
                            URI.create("ws://kingrabbit.dev/bunnyutils"),  // To test before pushing to webserver, use a localhost url
                            new SocketListener()
                    )
                    .get();

            Bunnyutils.LOGGER.info("Connected to server, sending handshake...");

            JsonObject handshakePacket = new JsonObject();
            handshakePacket.addProperty("type", "handshake");
            handshakePacket.addProperty("key", Config.apiKey);

            socket.sendText(Bunnyutils.GSON.toJson(handshakePacket), true);
        } catch (ExecutionException | InterruptedException exception) {
            connected = false;
            Bunnyutils.LOGGER.error("Unable to connection to socket server:");
            exception.printStackTrace();
        }
    }

}
