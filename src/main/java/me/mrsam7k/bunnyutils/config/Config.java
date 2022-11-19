package me.mrsam7k.bunnyutils.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screens.Screen;

public class Config extends MidnightConfig {
    @Comment(centered = true) public static Comment automationCategory;
    @Entry public static boolean autoGG = false;
    @Comment(centered = true) public static Comment wipCategory;
    @Entry public static boolean simplifiedActionbar = false;

    public static Screen getScreen(Screen parent){
        return MidnightConfig.getScreen(parent, "BunnyUtils");
    }


}