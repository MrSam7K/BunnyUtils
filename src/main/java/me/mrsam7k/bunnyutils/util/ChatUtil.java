package me.mrsam7k.bunnyutils.util;

import net.minecraft.client.Minecraft;

public class ChatUtil implements ITranslatable {

    public static void displayMessage(String message, ChatType chatType){
        Minecraft mc = Minecraft.getInstance();
        mc.player.displayClientMessage(ITranslatable.get(chatType.prefix + message.replace('&', '§')), false);
        if(chatType.sound != null){
            mc.player.playSound(chatType.sound);
        }
    }

}
