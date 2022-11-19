package me.mrsam7k.bunnyutils.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ConfigScreen extends MidnightConfig {
    @Comment(centered = true) public static Comment automationCategory;
    @Entry public static boolean autoGG = false;



    public static void open(Screen parent) {

        Screen screen = MidnightConfig.getScreen(parent, "BunnyUtils");
        Minecraft.getInstance().setScreen(screen);

    }


}
