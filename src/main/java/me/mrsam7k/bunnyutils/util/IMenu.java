package me.mrsam7k.bunnyutils.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public interface IMenu {
    default void scheduleOpenGui(LightweightGuiDescription gui, String... args) {
        try{
            this.open(args);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CottonClientScreen(gui)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void open(String... args) throws CommandSyntaxException, IOException;
}
