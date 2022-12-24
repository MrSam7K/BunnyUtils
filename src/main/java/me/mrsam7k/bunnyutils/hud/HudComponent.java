package me.mrsam7k.bunnyutils.hud;

import me.mrsam7k.bunnyutils.util.IntPair;

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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public IntPair getPos() {
        return new IntPair(x, y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract List<HudObject> render(float tickDelta);

}
