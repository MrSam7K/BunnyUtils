package me.mrsam7k.bunnyutils.event;

import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.util.MessageUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

import java.util.regex.Pattern;

public class JoinServerEvent implements ClientPlayConnectionEvents.Join {

    @Override
    public void onPlayReady(ClientPacketListener handler, PacketSender sender, Minecraft client) {
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
    }

}
