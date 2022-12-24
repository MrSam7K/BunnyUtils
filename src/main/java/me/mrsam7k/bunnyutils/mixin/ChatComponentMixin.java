package me.mrsam7k.bunnyutils.mixin;

import me.mrsam7k.bunnyutils.Bunnyutils;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.regex.Pattern;

@Mixin(ChatComponent.class)
public abstract class ChatComponentMixin extends GuiComponent {

    private static final String PUBLIC_REGEX = "(🌊 .+ 🌊 )?(.* )?[a-zA-Z0-9_]{3,16}: .+";
    private static final Pattern PUBLIC_PATTERN = Pattern.compile(PUBLIC_REGEX);
    private static final String PRIVATE_REGEX = "((\\[You » (.* )?[a-zA-Z0-9_]{3,16}\\] )|(\\[(.* )?[a-zA-Z0-9_]{3,16} » You] )).+";
    private static final Pattern PRIVATE_PATTERN = Pattern.compile(PRIVATE_REGEX);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public abstract int getWidth();

    @Shadow
    public abstract double getScale();

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V", at = @At("HEAD"), cancellable = true)
    private void addMessage(Component component, MessageSignature messageSignature, int i, GuiMessageTag guiMessageTag, boolean bl, CallbackInfo ci) {
        String message = component.getString();

        Bunnyutils.LOGGER.info(message);
        if (message.startsWith("§bYour API key has been updated to §3") && message.endsWith("§b. §8[§a§lCLICK§8]")) {
            Config.apiKey = message.split("(to )|(\\. )")[1].replaceAll("§.", "");
            MessageUtil.sendMessageWithPrefix("Detected new API key from chat.");
        }
        int k = Mth.floor((double) getWidth() / getScale());
        List<FormattedCharSequence> list = ComponentRenderUtils.wrapComponents(component, k, minecraft.font);
        addAll(Bunnyutils.GLOBAL_CHAT, list, guiMessageTag, i);
        String message = component.getString();
        int selected = Bunnyutils.chatSelected;
        if (message.startsWith("[STAFF] ")) {
            addAll(Bunnyutils.STAFF_CHAT, list, guiMessageTag, i);
            if (selected != 0 && selected != 3) ci.cancel();
        } else if (message.startsWith("[ADMIN] ")) {
            addAll(Bunnyutils.ADMIN_CHAT, list, guiMessageTag, i);
            if (selected != 0 && selected != 4) ci.cancel();
        } else if (!(message.startsWith("Latest patch") || message.startsWith("Current event")) && PUBLIC_PATTERN.matcher(message).matches()) {
            addAll(Bunnyutils.PUBLIC_CHAT, list, guiMessageTag, i);
            if (selected != 0 && selected != 1) ci.cancel();
        } else if (PRIVATE_PATTERN.matcher(message).matches()) {
            addAll(Bunnyutils.PRIVATE_CHAT, list, guiMessageTag, i);
            if (selected != 0 && selected != 2) ci.cancel();
        } else if (selected != 0) {
            ci.cancel();
        }
    }

    private void addAll(List<GuiMessage.Line> addTo, List<FormattedCharSequence> toAdd, GuiMessageTag messageTag, int addedTime) {
        int i = 0;
        for (FormattedCharSequence adding : toAdd) {
            i++;
            addTo.add(0, new GuiMessage.Line(addedTime, adding, messageTag, i == toAdd.size()));
        }
    }

}
