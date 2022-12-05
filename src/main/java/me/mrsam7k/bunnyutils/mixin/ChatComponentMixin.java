package me.mrsam7k.bunnyutils.mixin;

import me.mrsam7k.bunnyutils.Bunnyutils;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
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

    @Shadow public abstract int getWidth();

    @Shadow public abstract double getScale();

    @Shadow @Final private Minecraft minecraft;



    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;IIZ)V", at = @At("HEAD"))
    private void addMessage(Component component, int i, int j, boolean bl, CallbackInfo ci) {
        int k = Mth.floor((double)getWidth() / getScale());
        List<FormattedCharSequence> list = ComponentRenderUtils.wrapComponents(component, k, minecraft.font);
        addAll(Bunnyutils.GLOBAL_CHAT, list, i, j);
        String message = component.getString();
        if (message.startsWith("[STAFF] ")) {
            addAll(Bunnyutils.STAFF_CHAT, list, i, j);
        } else if (message.startsWith("[ADMIN] ")) {
            addAll(Bunnyutils.ADMIN_CHAT, list, i, j);
        } else if (PUBLIC_PATTERN.matcher(message).matches()) {
            addAll(Bunnyutils.PUBLIC_CHAT, list, i, j);
        } else if (PRIVATE_PATTERN.matcher(message).matches()) {
            addAll(Bunnyutils.PRIVATE_CHAT, list, i, j);
        }
    }

    private void addAll(List<GuiMessage<FormattedCharSequence>> addTo, List<FormattedCharSequence> toAdd, int i, int j) {
        for (FormattedCharSequence adding : toAdd) {
            addTo.add(0, new GuiMessage<>(j, adding, i));
        }
    }

}
