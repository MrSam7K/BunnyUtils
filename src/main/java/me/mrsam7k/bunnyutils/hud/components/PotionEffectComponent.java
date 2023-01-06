package me.mrsam7k.bunnyutils.hud.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.hud.HudComponent;
import me.mrsam7k.bunnyutils.hud.HudObject;
import me.mrsam7k.bunnyutils.hud.objects.HudString;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PotionEffectComponent  extends HudComponent {
    public PotionEffectComponent() {
        super("potion_effects");
        this.x = 5;
        this.y = 10;
    }

    @Override
    public List<HudObject> render(float tickDelta) throws URISyntaxException, IOException, InterruptedException {
        if(!Config.potionDisplay || Bunnyutils.tablistFooter == null) return new ArrayList<>();

        ArrayList<HudObject> arrayList;
        try {
            String[] footer = Bunnyutils.tablistFooter.getString().split("\n");
            if(getPotionStart(footer) == -1) return new ArrayList<>();
            JsonObject potionColors = Bunnyutils.potionColors;

            arrayList = new ArrayList<>();
            int i = getPotionStart(footer);
            while (i < footer.length - 2) {

                String[] potionData = footer[i].contains("of") ? footer[i].split(" ") : footer[i].split(" Potion ");
                String potionColor = footer[i].contains("of") ? potionColors.get(potionData[2].toLowerCase()).getAsString() : potionColors.get(potionData[0].toLowerCase()).getAsString();
                if (footer[i].contains("of")) {
                    arrayList.add(new HudString("&" + potionColor + "Potion of Disguise"));
                } else arrayList.add(new HudString("&" + potionColor + potionData[0] + " Potion " + potionData[1]));

                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

        return arrayList;
    }

    private static int getPotionStart(String[] content){
        int line = 0;
        for(String s : content){
            line++;
            if(s.contains("Active Potion")){
                return line;
            }
        }
        return -1;
    }
}
