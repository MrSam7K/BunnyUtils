package me.mrsam7k.bunnyutils.mixin.event;


import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.ui.ProfileViewerUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ContainerScreen.class)
public class InventoryOpen {

    @Inject(method = "render", at = @At("RETURN"))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.screen.getTitle().getString().contains("'s Profile")){

            ProfileViewerUI gui = new ProfileViewerUI();
            gui.scheduleOpenGui(gui, mc.screen.getTitle().getString().split("'s")[0]);
        }
    }
}
