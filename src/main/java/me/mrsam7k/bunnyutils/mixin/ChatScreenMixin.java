package me.mrsam7k.bunnyutils.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.util.ChatButtonInformation;
import me.mrsam7k.bunnyutils.util.ScreenUtil;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {

    private static final List<ChatButtonInformation> buttons = new ArrayList<>();

    protected ChatScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (minecraft == null) return;
        int offset = 0;
        buttons.clear();
        offset += chatButton(poseStack, 0, "GLOBAL", Bunnyutils.chatSelected == 0, offset);
        offset += chatButton(poseStack, 1, "PUBLIC", Bunnyutils.chatSelected == 1, offset);
        offset += chatButton(poseStack, 2, "PRIVATE MESSAGES", Bunnyutils.chatSelected == 2, offset);
        offset += chatButton(poseStack, 3, "STAFF CHAT", Bunnyutils.chatSelected == 3, offset);
        offset += chatButton(poseStack, 4, "ADMIN CHAT", Bunnyutils.chatSelected == 4, offset);
    }

    private int chatButton(PoseStack poseStack, int id, String name, boolean selected, int offset) {
        int nameWidth = minecraft.font.width(name);
        int x = 2 + offset;
        int y = this.height - 51 - minecraft.gui.getChat().getHeight();
        int x2 = 4 + nameWidth + offset;
        int y2 = this.height + minecraft.font.lineHeight - 50 - minecraft.gui.getChat().getHeight();
        fill(poseStack, x, y, x2, y2,
                selected ? this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE) / 2 : this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE));
        drawString(poseStack, minecraft.font, name, 3 + offset, this.height - 50 - minecraft.gui.getChat().getHeight(), 0xFFFFFF);

        buttons.add(new ChatButtonInformation(id, x, y, x2, y2));

        return nameWidth + 4;
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void mouseClicked(double mouseX, double mouseY, int unused, CallbackInfoReturnable<Boolean> cir) {
        for (ChatButtonInformation information : buttons) {
            if (ScreenUtil.hovering(mouseX, mouseY, information.x1(), information.y1(), information.x2(), information.y2())) {
                Bunnyutils.chatSelected = information.id();

                

                cir.setReturnValue(true);
                cir.cancel();
                return;
            }
        }
    }

}
