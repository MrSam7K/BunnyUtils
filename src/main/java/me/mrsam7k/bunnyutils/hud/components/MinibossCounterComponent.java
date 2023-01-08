package me.mrsam7k.bunnyutils.hud.components;

import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.hud.HudComponent;
import me.mrsam7k.bunnyutils.hud.HudObject;
import me.mrsam7k.bunnyutils.hud.objects.HudString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinibossCounterComponent extends HudComponent {
    public MinibossCounterComponent() {
        super("miniboss_counter");
        this.x = 5;
        this.y = 10;
    }

    @Override
    public List<HudObject> render(float tickDelta) {
        if(!Config.minibossCounter) return new ArrayList<>();
        try {
            return List.of(
                    new HudString("&7Miniboss Counter: &6" + Bunnyutils.minibossCounter)
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
