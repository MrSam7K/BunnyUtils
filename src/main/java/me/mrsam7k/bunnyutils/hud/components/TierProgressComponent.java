package me.mrsam7k.bunnyutils.hud.components;

import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.hud.HudComponent;
import me.mrsam7k.bunnyutils.hud.HudObject;
import me.mrsam7k.bunnyutils.hud.objects.HudString;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TierProgressComponent extends HudComponent {

    public TierProgressComponent() {
        super("tier_progress");
        this.x = 5;
        this.y = 5;
    }

    private static int findIndex(String[] arr, String t) {
        if (arr == null) {
            return -1;
        }
        int i = 0;
        for (String entry : arr) {
            if (Objects.equals(entry.replaceAll("§.", ""), t.replaceAll("§.", ""))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public List<HudObject> render(float tickDelta) {
        if (!Config.progressDisplay) return new ArrayList<>();

        Component tablistFooter = Bunnyutils.tablistFooter;
        if (tablistFooter == null) return Arrays.asList(
                new HudString("&7Your tier: &cUNKNOWN"),
                new HudString("&7Next tier: &cUNKNOWN")
        );

        String[] footer = tablistFooter.getString().split("\n");
        if (footer.length < 2) return Arrays.asList(
                new HudString("&7Your tier: &cUNKNOWN"),
                new HudString("&7Next tier: &cUNKNOWN")
        );

        String currentTier = footer[1].split(": ")[1];
        String percentage;
        int nextTierNum;
        String nextTier;
        if (!currentTier.equals("Divinity ☁☁☁☁☁")) {
            nextTierNum = findIndex(Bunnyutils.bfTiers, currentTier) + 1;
            nextTier = nextTierNum == -1 ? "Failed to load" : Bunnyutils.bfTiers[nextTierNum];

            if (footer.length < 4) return Arrays.asList(
                    new HudString("&7" + footer[1].replaceAll(": ", ": &e")),
                    new HudString("&7Next tier: &e" + nextTier)
            );

            String[] percentageParts = footer[3].split(" ");
            if (percentageParts.length < 2) return Arrays.asList(
                    new HudString("&7" + footer[1].replaceAll(": ", ": &e")),
                    new HudString("&7Next tier: &e" + nextTier)
            );

            percentage = percentageParts[1];
        } else {
            percentage = " ";
            nextTier = "&6MAXED";
        }

        return Arrays.asList(
                new HudString("&7" + footer[1].replaceAll(": ", ": &e")),
                new HudString("&7Next tier: &e" + nextTier + " &8" + percentage)
        );
    }

}
