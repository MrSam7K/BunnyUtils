package me.mrsam7k.bunnyutils;

import eu.midnightdust.lib.config.MidnightConfig;
import me.mrsam7k.bunnyutils.config.Config;
import net.fabricmc.api.ModInitializer;

public class Bunnyutils implements ModInitializer {

    /**
     * 0 - GLOBAL
     * 1 - PUBLIC
     * 2 - PRIVATE MESSAGES
     * 3 - STAFF CHAT
     * 4 - ADMIN CHAT
     */
    public static int chatSelected = 0;
    public static boolean lastGG;
    public static boolean vanished;

    @Override
    public void onInitialize() {
        MidnightConfig.init("BunnyUtils", Config.class);
    }
}
