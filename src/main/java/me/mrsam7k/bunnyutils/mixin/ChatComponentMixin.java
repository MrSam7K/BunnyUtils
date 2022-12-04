package me.mrsam7k.bunnyutils.mixin;

import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ChatComponent.class)
public interface ChatComponentMixin {

    @Accessor
    List<GuiMessage<FormattedCharSequence>> getTrimmedMessages();

}
