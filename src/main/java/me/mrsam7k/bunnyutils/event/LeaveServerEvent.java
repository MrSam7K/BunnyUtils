package me.mrsam7k.bunnyutils.event;

import me.mrsam7k.bunnyutils.discordRPC.DiscordRPC;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public class LeaveServerEvent implements ClientPlayConnectionEvents.Disconnect {
    @Override
    public void onPlayDisconnect(ClientPacketListener handler, Minecraft client) {
    }
}
