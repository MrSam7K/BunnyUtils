package me.mrsam7k.bunnyutils;

import eu.midnightdust.lib.config.MidnightConfig;
import me.mrsam7k.bunnyutils.config.Config;
import net.fabricmc.api.ModInitializer;
import net.minecraft.network.chat.Component;

public class Bunnyutils implements ModInitializer {
    public static boolean lastGG;
    public static boolean vanished;
    public static Component tablistFooter;
    public static String[] bfTiers = {
            "Iron I","Iron II","Iron III","Iron IV","Iron V",
            "Gold I","Gold II","Gold III","Gold IV","Gold V",
            "Diamond I","Diamond II","Diamond III","Diamond IV","Diamond V",
            "Emerald I","Emerald II","Emerald III","Emerald IV","Emerald V",
            "Ruby I","Ruby II","Ruby III","Ruby IV","Ruby V",
            "Jr. Bunny I","Jr. Bunny II","Jr. Bunny III","Jr. Bunny IV","Jr. Bunny V",
            "Bunny I","Bunny II","Bunny III","Bunny IV","Bunny V",
            "Master ⭐","Master ⭐⭐","Master ⭐⭐⭐","Master ⭐⭐⭐⭐","Master ⭐⭐⭐⭐⭐",
            "Overlord \uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25",
            "Eminence ☀","Eminence ☀☀","Eminence ☀☀☀","Eminence ☀☀☀☀","Eminence ☀☀☀☀☀",
            "Divinity ☁","Divinity ☁☁","Divinity ☁☁☁","Divinity ☁☁☁☁","Divinity ☁☁☁☁☁",

    };

    @Override
    public void onInitialize() {
        System.out.println("BunnyUtils is initializing!");
        MidnightConfig.init("BunnyUtils", Config.class);
        System.out.println("BunnyUtils finished initializing!");
    }
}
