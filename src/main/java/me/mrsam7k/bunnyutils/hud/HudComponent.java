package me.mrsam7k.bunnyutils.hud;

import java.util.List;

public abstract class HudComponent {

    private final String id;
    protected int x;
    protected int y;

    public HudComponent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract List<HudObject> render(float tickDelta);

}
