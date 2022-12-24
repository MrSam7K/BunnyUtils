package me.mrsam7k.bunnyutils.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import me.mrsam7k.bunnyutils.util.IntPair;

public abstract class HudObject {

    public abstract IntPair getDimensions();

    public abstract IntPair render(PoseStack matrices, float tickDelta, int x, int y);

}
