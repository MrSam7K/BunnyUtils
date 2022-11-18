package me.mrsam7k.bunnyutils.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen implements ITranslatable{

    public static void open() {
        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(Component.nullToEmpty("title.bunnyutils.config"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory automation = builder.getOrCreateCategory(Component.nullToEmpty("category.bunnyutils.automation"))
                .addEntry(entryBuilder.startBooleanToggle(ITranslatable.get("hello"), false)
                        .setDefaultValue(false)
                        .setTooltip(ITranslatable.get("Toggles whether player says GG after boss fight or prestige"))
                        .build());

        Screen screen = builder.build();
        Minecraft.getInstance().setScreen(screen);
    }


}
