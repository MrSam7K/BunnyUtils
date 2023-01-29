package me.mrsam7k.bunnyutils.mixin.event;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.util.ITranslatable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class TablistUpdate implements ITranslatable {

    @Inject(at = @At("RETURN"), method = "setFooter")
    private void getTablist(Component component, CallbackInfo ci) {
        Bunnyutils.discordPresence.put("tier", component.getString().split("\n")[1].split(": ")[1]);
        Bunnyutils.tablistFooter = component;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I"))
    private int renderBunnyUtilIcon(Font instance, PoseStack poseStack, Component playerName, float x, float y, int colorMultiplier) {
        Minecraft.getInstance().player.connection.getOnlinePlayers().forEach(playerInfo -> {
            if (playerInfo.getTabListDisplayName() != null) {
            if (playerInfo.getTabListDisplayName().equals(playerName)) {
                if (Bunnyutils.PLAYERS_WITH_MOD.contains(playerInfo.getProfile().getId())) {
                    // Player is using the mod
                    instance.drawShadow(poseStack, "\u2B50", x - 12, y - 3, 0xFFAA00);
                }
            }
        }
        });
        return instance.drawShadow(poseStack, playerName, x, y, colorMultiplier);  // Still want to render their name, just needed to get the x, y, and name
    }

}
