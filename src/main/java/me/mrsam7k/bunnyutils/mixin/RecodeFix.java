package me.mrsam7k.bunnyutils.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Button.class)
public class RecodeFix {

    @ModifyArgs(method = "<init>(IIIILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;<init>(IIIILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;Lnet/minecraft/client/gui/components/Button$OnTooltip;)V"))
    private static void fixRecord(Args args) {
        if (args.get(4).equals(Component.literal("Recode"))) {
            // Move recode button to the right
            args.set(0, Minecraft.getInstance().screen.width / 2 + 5);
        }
    }

}
