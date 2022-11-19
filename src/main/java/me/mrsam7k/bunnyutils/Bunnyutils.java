package me.mrsam7k.bunnyutils;

import eu.midnightdust.lib.config.MidnightConfig;
import me.mrsam7k.bunnyutils.config.Config;
import net.fabricmc.api.ModInitializer;

    public class Bunnyutils implements ModInitializer {
    public static boolean lastGG;
    public static boolean vanished;

    @Override
    public void onInitialize() {
        MidnightConfig.init("BunnyUtils", Config.class);
    }
}
