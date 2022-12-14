package me.mrsam7k.bunnyutils.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.util.IntPair;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public final class HudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        for (HudComponent component : HudManager.getInstance().getComponents()) {
            List<HudObject> objects = null;
            try {
                objects = component.render(tickDelta);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (objects.isEmpty()) continue;
            int x = component.getX() + 2;
            int y = component.getY() + 2;
            int largestWidth = 0;
            for (HudObject object : objects) {
                IntPair pair = object.getDimensions();
                if (pair.first_integer() > largestWidth) largestWidth = pair.first_integer();
                y += pair.second_integer() + 1;
            }
            int hex = Color.HSBtoRGB(0, 0, Config.intSlider);
            GuiComponent.fill(matrixStack, component.getX(), component.getY(), x + largestWidth + 1, y, 0xBF000000);
            x = component.getX() + 2;
            y = component.getY() + 2;
            for (HudObject object : objects) {
                IntPair pair = object.render(matrixStack, tickDelta, x, y);
                y += pair.second_integer() + 1;
            }
        }
    }

}
