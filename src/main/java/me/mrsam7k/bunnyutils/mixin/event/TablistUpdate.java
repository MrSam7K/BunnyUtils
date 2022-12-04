package me.mrsam7k.bunnyutils.mixin.event;

import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.ITranslatable;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class TablistUpdate implements ITranslatable {

    @Inject(at = @At("RETURN"), method = "setFooter")
    private void getTablist(Component component, CallbackInfo ci) {
            Bunnyutils.tablistFooter = component;
        }



}
