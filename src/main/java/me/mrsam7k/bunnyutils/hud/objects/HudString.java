package me.mrsam7k.bunnyutils.hud.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.hud.HudObject;
import me.mrsam7k.bunnyutils.util.IntPair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

public class HudString extends HudObject {

    String string;

    public HudString(String string) {
        this.string = string;
    }

    @Override
    public IntPair getDimensions(PoseStack matrices, float tickDelta, int x, int y) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        int width = 0;
        if (string.contains("&")) {
            int currentX = x;
            String[] parts = string.split("&");
            parts[0] = "f" + parts[0];
            for (String part : parts) {
                String textSegment = part.substring(1);
                int segmentWidth = font.width(textSegment);
                currentX += segmentWidth;
                width += segmentWidth;
            }
        } else {
            width = font.width(string);
        }

        return new IntPair(width, font.lineHeight);
    }

    @Override
    public IntPair render(PoseStack matrices, float tickDelta, int x, int y) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        int width = 0;
        if (string.contains("&")) {
            int currentX = x;
            String[] parts = string.split("&");
            parts[0] = "f" + parts[0];
            for (String part : parts) {
                char colourChar = part.charAt(0);
                ChatFormatting colour = ChatFormatting.getByCode(colourChar);
                String textSegment = part.substring(1);
                font.drawShadow(matrices, textSegment, currentX, y, colour.getColor());
                int segmentWidth = font.width(textSegment);
                currentX += segmentWidth;
                width += segmentWidth;
            }
        } else {
            font.drawShadow(matrices, string, x, y, 0xFFFFFF);
            width = font.width(string);
        }

        return new IntPair(width, font.lineHeight);
    }

}
