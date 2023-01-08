package me.mrsam7k.bunnyutils.mixin.event;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.Bunnyutils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class RenderNameTag<T extends Entity> {
    @Inject(method = "renderNameTag", at = @At("RETURN"))
    public void renderNameTag(T entity, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if(component.getString().contains("[") || component.getString().contains("/") || component.getString().contains("]")){
            Bunnyutils.minibossCounter = component.getString().replace("[", "").replace("]", "");
        }
    }
}
