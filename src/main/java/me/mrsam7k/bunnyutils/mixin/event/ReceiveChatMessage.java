package me.mrsam7k.bunnyutils.mixin.event;


import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.config.ITranslatable;
import me.mrsam7k.bunnyutils.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPacketListener.class)
public abstract class ReceiveChatMessage implements ITranslatable {

    @Inject(method = "handleSystemChat", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {

        try {

        boolean gg = false;
        Minecraft mc = Minecraft.getInstance();
        String msg = packet.content().getString().replaceAll("§.", "");
        String msgWithColor = TextUtil.textComponentToColorCodes(packet.content());
        String message = msgWithColor.replaceAll("§.", "");
        if(message.contains("Bunny Points") && message.contains("Bunny Stars")){
            Bunnyutils.actionBar = msgWithColor;
            if(Config.bundleProgress == Config.bundleOptions.ACTION_BAR) {
                ci.cancel();
                bunnyBundleProgress(msgWithColor);
            }
            if(Config.elixirExchangeNotif) elixirExchangeNotif(msg);
            return;
        } else Bunnyutils.actionBar = "";

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
            if(Minecraft.getInstance().player.experienceLevel > 0){
                String bunny = Minecraft.getInstance().player.experienceLevel == 1 ? " Bunny " : " Bunnies ";
                Minecraft.getInstance().player.displayClientMessage(ITranslatable.get(s + " §8- §f" + Minecraft.getInstance().player.experienceLevel + bunny + "Left"), true);
            } else {
                int bundleProgress = (int) (Minecraft.getInstance().player.experienceProgress * 100);
                Minecraft.getInstance().player.displayClientMessage(ITranslatable.get(s + " §8- §f" + bundleProgress + "% Bunny Bundle"), true);
            }


        } catch(Exception ignored){}
    }

    private void elixirExchangeNotif(String s){
        String[] actionBar = s.split("-");
        if(getElixir(actionBar) % 1 == 0){
            if(Bunnyutils.lastElixirExchange != getElixir(actionBar)){
                Component msg = getElixir(actionBar) == 1 ? ITranslatable.get("\n§7[§fBU§7] §3You can exchange elixir once!\n ") : ITranslatable.get("\n§7[§fBU§7] §3You can exchange elixir " + (int) getElixir(actionBar) + " times!\n ");
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.displayClientMessage(msg, false);
                Minecraft.getInstance().player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
                Bunnyutils.lastElixirExchange = getElixir(actionBar);
            }
        } else {
            Bunnyutils.lastElixirExchange = 0;
        }

    }

    private double getElixir(String[] actionBar){
        for(String s : actionBar){
            if(s.contains("Elixir")){
                return (double) Integer.parseInt(s.replaceAll("Elixir", "").replaceAll(" ", "")) / 100;
            }
        }
        return 0;
    }
}
