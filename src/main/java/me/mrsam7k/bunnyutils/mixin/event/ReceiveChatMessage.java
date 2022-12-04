package me.mrsam7k.bunnyutils.mixin.event;


import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.config.ITranslatable;
import me.mrsam7k.bunnyutils.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.TimeUnit;


@Mixin(ClientPacketListener.class)
public abstract class ReceiveChatMessage implements ITranslatable {

    //@Shadow public abstract void send(Packet<?> packet);

    @Shadow public abstract void cleanup();

    @Inject(method = "handleSystemChat", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {

        try {

        boolean gg = false;
        Minecraft mc = Minecraft.getInstance();
        String msg = packet.content().getString().replaceAll("§.", "");
        String msgWithColor = TextUtil.textComponentToColorCodes(packet.content());
        String message = msgWithColor.replaceAll("§.", "");
        if(message.contains("Bunny Points") && message.contains("Bunny Stars")){
            if(Config.bundleProgress) {
                ci.cancel();
                bunnyBundleProgress(msgWithColor);
            }
            return;
        }

        if (Minecraft.getInstance().player != null) {

            if (message.contains(" is now ") && message.contains("! Congratulations!") && !message.contains(": ") && !message.contains(mc.player.getName().getString())) {
                gg = true;
            } else if (message.contains("The ") && message.contains("Bunny") && message.contains(" has been defeated!")  && !message.contains(": ")
            ) {
                gg = true;
            }
            if (gg) {
                if (Bunnyutils.lastGG) {
                    Bunnyutils.lastGG = false;
                    return;
                }
                Bunnyutils.lastGG = true;
                if (Bunnyutils.vanished) {
                    return;
                }
                if(!Config.autoGG) { return; }
                mc.player.chat("GG");

            }

            //Vanish checks for staff
            if (message.contains( mc.player.getName().getString()) && message.contains(" joined silently.")) {
                Bunnyutils.vanished = true;
            }
            if (message.contains("You are no longer vanished.")) {
                Bunnyutils.vanished = false;
            }
            if (message.contains("You are now vanished.")) {
                Bunnyutils.vanished = true;
            }
        }
        } catch(Exception ex){System.out.println(ex);}
    }

    private static void bunnyBundleProgress(String s){
        assert Minecraft.getInstance().player != null;
        try {
            int bundleProgress = (int) (Minecraft.getInstance().player.experienceProgress * 100);
            Minecraft.getInstance().player.displayClientMessage(ITranslatable.get(s + " §8- §f" + bundleProgress + "% Bunny Bundle"), true);
        } catch(Exception ignored){}
    }
}
