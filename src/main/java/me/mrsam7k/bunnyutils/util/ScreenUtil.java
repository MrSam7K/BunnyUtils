package me.mrsam7k.bunnyutils.util;

public final class ScreenUtil {

    /**
     * Determines if the given mouse coordinates are hovering over a region
     * @param mouseX The mouse X coordinate
     * @param mouseY The mouse Y coordinate
     * @param x1 The region's first X coordinate
     * @param y1 The region's first Y coordinate
     * @param x2 The region's second X coordinate
     * @param y2 The region's second Y coordinate
     * @return If the given mouse coordinates are hovering over a region
     */
    public static boolean hovering(double mouseX, double mouseY, int x1, int y1, int x2, int y2) {
        if (x1 > x2) return hovering(mouseX, mouseY, x2, y1, x1, y2);
        if (y1 > y2) return hovering(mouseX, mouseY, x1, y2, x2, y1);
        return x1 <= mouseX && mouseX <= x2 &&
                y1 <= mouseY && mouseY <= y2;
    }

}
