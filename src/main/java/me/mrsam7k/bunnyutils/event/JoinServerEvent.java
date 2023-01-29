package me.mrsam7k.bunnyutils.event;

import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.discordRPC.DiscordRPC;
import me.mrsam7k.bunnyutils.socket.SocketHandler;
import me.mrsam7k.bunnyutils.util.MessageUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

import java.io.IOException;

public class JoinServerEvent implements ClientPlayConnectionEvents.Join {

    @Override
    public void onPlayReady(ClientPacketListener handler, PacketSender sender, Minecraft client) {
        Bunnyutils.initTiers();
        Bunnyutils.initPotionColors();
        try {
            DiscordRPC.discordRPC();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (Config.apiKey == null || Config.apiKey.isEmpty() || !Config.apiKey.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
            // API key is definitely invalid - inform user
            Bunnyutils.LOGGER.info("API key is definitely invalid");

            String[] messages = {
                    "&3--------------------",
                    "&bYour API key needs to be updated!",
                    "&bTo update your API key and unlock all of BunnyUtils's features, run &9/api&b or click (&9here)[run:/api]&b.",
                    "&3--------------------"
            };
            MessageUtil.sendMessages(messages);
        }

        if (!SocketHandler.connected) {
            // If haven't connected to socket yet, attempt to connect.
            new SocketHandler();
        }
    }

}
