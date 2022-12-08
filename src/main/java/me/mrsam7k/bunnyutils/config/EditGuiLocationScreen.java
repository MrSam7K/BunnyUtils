package me.mrsam7k.bunnyutils.config;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.hud.HudComponent;
import me.mrsam7k.bunnyutils.hud.HudManager;
import me.mrsam7k.bunnyutils.hud.HudObject;
import me.mrsam7k.bunnyutils.util.IntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EditGuiLocationScreen extends Screen {

    private Screen parent;
    private String currentlyMoving = null;

    public EditGuiLocationScreen(Screen parent) {
        super(Component.translatable("bunnyutils.config.gui_locations"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 29, 200, 20, CommonComponents.GUI_DONE, button -> onClose()));
        Bunnyutils.movingComponents = true;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
        Bunnyutils.movingComponents = false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int oldX = (int) (mouseX-deltaX);
        int oldY = (int) (mouseY-deltaY);

        for (HudComponent component : HudManager.getInstance().getComponents()) {
            List<HudObject> objects = component.render(0);
            int x = component.getX() + 2;
            int y = component.getY() + 2;
            int largestWidth = 0;
            for (HudObject object : objects) {
                IntPair pair = object.getDimensions();
                if (pair.first_integer() > largestWidth) largestWidth = pair.first_integer();
                y += pair.second_integer() + 1;
            }
            if (isHovering(oldX, oldY, component.getX(), component.getY(), x + largestWidth + 1, y) && shouldMove(component.getId())) {
                currentlyMoving = component.getId();
                component.setPos((int) mouseX, (int) mouseY);
                return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    private boolean isHovering(double mouseX, double mouseY, int x1, int y1, int x2, int y2) {
        return
                x1 <= mouseX && mouseX <= x2 &&
                y1 <= mouseY && mouseY <= y2;
    }

    private boolean shouldMove(String id) {
        return currentlyMoving == null || currentlyMoving.equals(id);
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        if (Minecraft.getInstance().level == null) {
            renderDirtBackground(0);

            for (HudComponent component : HudManager.getInstance().getComponents()) {
                List<HudObject> objects = component.render(delta);
                int x = component.getX() + 2;
                int y = component.getY() + 2;
                int largestWidth = 0;
                for (HudObject object : objects) {
                    IntPair pair = object.getDimensions();
                    if (pair.first_integer() > largestWidth) largestWidth = pair.first_integer();
                    y += pair.second_integer() + 1;
                }
                GuiComponent.fill(matrices, component.getX(), component.getY(), x + largestWidth + 1, y, 0xBF000000);
                x = component.getX() + 2;
                y = component.getY() + 2;
                for (HudObject object : objects) {
                    IntPair pair = object.render(matrices, delta, x, y);
                    y += pair.second_integer() + 1;
                }
            }
        }

        GuiComponent.drawCenteredString(matrices, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

}
