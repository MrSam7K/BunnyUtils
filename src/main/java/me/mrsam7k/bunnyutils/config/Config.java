package me.mrsam7k.bunnyutils.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screens.Screen;

public class Config extends MidnightConfig {

    @Comment(centered = true) public static Comment fillerComment;

    @Comment(centered = true) public static Comment automationCategory;
    @Entry public static boolean autoGG = false;

    @Comment(centered = true) public static Comment screenCategory;
    @Entry public static boolean progressDisplay = true;
    @Entry public static boolean bundleProgress = false;
    @Entry public static boolean potionDisplay = false;
    @Entry public static boolean chatTabs = true;
    @Entry public static boolean bfButton = true;

    @Comment(centered = true) public static Comment miscCategory;
    @Entry public static boolean elixirExchangeNotif = false;

    public static Screen getScreen(Screen parent){
        return MidnightConfig.getScreen(parent, "BunnyUtils");
    }


}
