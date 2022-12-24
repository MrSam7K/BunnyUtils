package me.mrsam7k.bunnyutils.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.ArrayList;
import java.util.List;

public final class HudManager {

    private static HudManager instance;

    private final List<HudComponent> components = new ArrayList<>();

    private HudManager() {
        instance = this;
        HudRenderCallback.EVENT.register(new HudRenderer());
    }

    public void renderComponent(HudComponent component) {
        components.add(component);
    }

    public List<HudComponent> getComponents() {
        return components;
    }

    public static HudManager getInstance() {
        if (instance == null) return new HudManager();
        return instance;
    }

}
