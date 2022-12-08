package me.mrsam7k.bunnyutils.mixin;

import eu.midnightdust.lib.config.MidnightConfig;
import me.mrsam7k.bunnyutils.config.EditGuiLocationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MidnightConfig.MidnightConfigScreen.class)
public class MidnightConfigMixin extends Screen {

    protected MidnightConfigMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("HEAD"))
    public void addGuiLocationsButton(CallbackInfo ci) {
        addRenderableWidget(new Button(this.width / 2 - 75, 40, 150, 20, Component.translatable("bunnyutils.config.gui_locations"), button -> {
            Minecraft.getInstance().setScreen(new EditGuiLocationScreen(this));
        }));
    }

}
