package me.mrsam7k.bunnyutils.hud.components;

import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.hud.HudComponent;
import me.mrsam7k.bunnyutils.hud.HudObject;
import me.mrsam7k.bunnyutils.hud.objects.HudString;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class BunnyBundleComponent extends HudComponent {
    public BunnyBundleComponent() {
        super("bunny_bundle");
        this.x = 10;
        this.y = 10;
    }

    @Override
    public List<HudObject> render(float tickDelta) {
        if(Config.bundleProgress != Config.bundleOptions.WINDOW) return new ArrayList<>();
        if(Minecraft.getInstance().player == null) return new ArrayList<>();

        String s = Bunnyutils.actionBar;
        String string;

        if(Minecraft.getInstance().player.experienceLevel > 0){
            String bunny = Minecraft.getInstance().player.experienceLevel == 1 ? " Bunny " : " Bunnies ";
            string = "§f" + Minecraft.getInstance().player.experienceLevel + bunny + "Left";
        } else {
            int bundleProgress = (int) (Minecraft.getInstance().player.experienceProgress * 100);
            string = "§f" + bundleProgress + "% Bunny Bundle";
        }

        return List.of(
                new HudString(string)
        );
    }
}
