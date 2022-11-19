package me.mrsam7k.bunnyutils.mixin.event;

import me.mrsam7k.bunnyutils.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSigner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class SendMessage {

    @Inject(method = "sendChat", at = @At("HEAD"), cancellable = true)
    public void chat(MessageSigner messageSigner, String string, Component component, CallbackInfo ci) {

        if(string.equals(".config")){
            ConfigScreen.open(Minecraft.getInstance().screen);
            ci.cancel();
        }

    }

}
