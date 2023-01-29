package me.mrsam7k.bunnyutils.util;

import net.minecraft.client.Minecraft;

public class PlayerUtil {

    public static boolean isOnBf(){
        Minecraft mc = Minecraft.getInstance();
        String ip = mc.player.getServer().getLocalIp();
        return ip.contains("mcbunnyfarm.org");
    }

}
