package me.mrsam7k.bunnyutils.ui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ProfileViewerUI extends LightweightGuiDescription implements IMenu {

    @Override
    public void open(String... args) throws IOException, InterruptedException, URISyntaxException {
        WPlainPanel root = new WPlainPanel();
        root.setSize(300, 220);

        addMenuItems(root, args[0]);
        setRootPanel(root);
    }

    private void addMenuItems(WPlainPanel panel, String target) throws IOException, InterruptedException, URISyntaxException {
        String UUIDJson;
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + target;
            UUIDJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            if (UUIDJson.isEmpty()) {
                ChatUtil.displayMessage("Player was not found!", ChatType.ERROR);
                return;
            }
        } catch (Exception ex) {
            ChatUtil.displayMessage("Something went wrong, you probably got rate-limited by Mojang.\n\n" + ex, ChatType.ERROR);
            return;
        }
        JsonObject json = JsonParser.parseString(UUIDJson).getAsJsonObject();
        String uuid = json.get("id").getAsString();
        String fullUUID = TextUtil.fromTrimmed(uuid);

        URI URI = new URI("https://mcbunnyfarm.org/api/player?uuid=" + fullUUID);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Api-Key", Config.apiKey)
                .uri(URI).build();
        HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject playerData = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        WText targetText = target.endsWith("s") ? new WText(ITranslatable.get(target + "' Profile")) : new WText(ITranslatable.get(target + "'s Profile"));
        targetText.setSize(10, 10);
        targetText.setColor(5);
        panel.add(targetText, 5, 5, 300, 20);

        //Statistics
        String[] stats = {"prestige", "points", "stars", "elixir", "slimeballs", "alchemy_level", "magic_dust", "nether_wart", "bunny_shards"};
        int y = 30;
        for(String stat : stats){
            panel.add(new WText(ITranslatable.get(TextUtil.toProperCase(stat.replace("_", " ")) + ": " + TextUtil.toFancyNumber(playerData.get(stat).getAsInt()))), 5, y, 300, 20);
            y += 10;
        }

        // Inventory Button
        WButton invButton = new WButton(Component.literal("Inventory"));
        invButton.setOnClick(() -> {
            Minecraft mc = Minecraft.getInstance();
            //Future inventory code
        });
        panel.add(invButton, 100, 192, 100, 20);
    }

}
