package me.mrsam7k.bunnyutils.mixin.event;

import me.mrsam7k.bunnyutils.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.screens.OptionsScreen.class)
public class OptionsScreen extends Screen {

    public OptionsScreen(Component literalText) {
        super(literalText);
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    protected void init(CallbackInfo callbackInfo) {
        Minecraft mc = Minecraft.getInstance();

        this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 6 + 144 - 6, 150, 20, Component.literal("BunnyUtils"), (buttonWidget) -> mc.setScreen(Config.getScreen(mc.screen))));
    }
}
