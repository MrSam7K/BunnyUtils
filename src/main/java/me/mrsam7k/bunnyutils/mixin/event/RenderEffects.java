package me.mrsam7k.bunnyutils.mixin.event;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.util.ITranslatable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(Gui.class)
public class RenderEffects {
    @Inject(method = "renderEffects", at = @At("RETURN"))
    private void renderEffects(PoseStack stack, CallbackInfo ci) {
        try {
            if(Config.potionDisplay) potionDisplay(stack);
            } catch(Exception ignored){}
    }
    private static void drawTextLeft(Component text, int line, Font tr, PoseStack stack) {
        text.getVisualOrderText();
        //int x = Minecraft.getInstance().getWindow().getGuiScaledWidth() - tr.width(text) - 4;
        int y = Minecraft.getInstance().getWindow().getGuiScaledHeight() - (Minecraft.getInstance().font.lineHeight * line + 4);
        tr.drawShadow(stack, text, 4, y, 0xffffff);
    }


    private static void potionDisplay(PoseStack stack){
        Font tr = Minecraft.getInstance().font;
        String[] footer = Bunnyutils.tablistFooter.getString().split("\n");
        int i = getPotionStart(footer);
        while(i < footer.length - 2){
            String[] potionData = footer[i].contains("of") ? footer[i].split(" ") : footer[i].split(" Potion ");
            Component potion = footer[i].contains("of") ? getPotionColor(potionData[2], "") : getPotionColor(potionData[0], potionData[1]);
            drawTextLeft(ITranslatable.get("Active Potion Effects").copy().withStyle(ChatFormatting.GRAY), 26 - getPotionStart(footer), tr, stack);
            drawTextLeft(potion, 25 - i, tr, stack);
            i++;
        }
    }

    private static int getPotionStart(String[] content){
        int line = 0;
        for(String s : content){
            line++;
            if(s.contains("Active Potion")){
                return line;
            }
        }
        return -1;
    }

    private static Component getPotionColor(String potion, String duration){
        Map<String, Component> map = new HashMap<>();
        map.put("Speed", ITranslatable.get("Speed Potion " + duration).copy().withStyle(ChatFormatting.AQUA));
        map.put("Sneaky", ITranslatable.get("Sneaky Potion " + duration).copy().withStyle(ChatFormatting.BLUE));
        map.put("Magical", ITranslatable.get("Magical Potion " + duration).copy().withStyle(ChatFormatting.DARK_AQUA));
        map.put("Turtle", ITranslatable.get("Turtle Potion " + duration).copy().withStyle(ChatFormatting.GREEN));
        map.put("Fisher", ITranslatable.get("Fisher Potion " + duration).copy().withStyle(ChatFormatting.AQUA));
        map.put("Treasure", ITranslatable.get("Treasure Potion " + duration).copy().withStyle(ChatFormatting.GOLD));
        map.put("Slime", ITranslatable.get("Slime Potion " + duration).copy().withStyle(ChatFormatting.DARK_GREEN));
        map.put("Hunter", ITranslatable.get("Hunter Potion " + duration).copy().withStyle(ChatFormatting.GREEN));
        map.put("Soul", ITranslatable.get("Soul Potion " + duration).copy().withStyle(ChatFormatting.DARK_AQUA));
        map.put("Teleportation", ITranslatable.get("Teleportation Potion " + duration).copy().withStyle(ChatFormatting.BLUE));
        map.put("Disguise", ITranslatable.get("Potion of Disguise").copy().withStyle(ChatFormatting.DARK_AQUA));
        map.put("Wither", ITranslatable.get("Wither Potion " + duration).copy().withStyle(ChatFormatting.DARK_PURPLE));
        return map.get(potion);
    }

}