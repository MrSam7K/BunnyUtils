package me.mrsam7k.bunnyutils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.event.JoinServerEvent;
import me.mrsam7k.bunnyutils.socket.SocketHandler;
import me.mrsam7k.bunnyutils.hud.HudManager;
import me.mrsam7k.bunnyutils.hud.components.PotionEffectComponent;
import me.mrsam7k.bunnyutils.hud.components.BunnyBundleComponent;
import me.mrsam7k.bunnyutils.hud.components.TierProgressComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.GuiMessage;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Bunnyutils implements ModInitializer {

    public static final Gson GSON = new Gson();
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final List<UUID> PLAYERS_WITH_MOD = new ArrayList<>();

    public static final List<GuiMessage.Line> GLOBAL_CHAT = new ArrayList<>();
    public static final List<GuiMessage.Line> PUBLIC_CHAT = new ArrayList<>();
    public static final List<GuiMessage.Line> PRIVATE_CHAT = new ArrayList<>();
    public static final List<GuiMessage.Line> STAFF_CHAT = new ArrayList<>();
    public static final List<GuiMessage.Line> ADMIN_CHAT = new ArrayList<>();

    /**
     * 0 - GLOBAL
     * 1 - PUBLIC
     * 2 - PRIVATE MESSAGES
     * 3 - STAFF CHAT
     * 4 - ADMIN CHAT
     */
    public static int chatSelected = 0;

    public static boolean lastGG;
    public static boolean vanished;
    public static Component tablistFooter;
    public static Component potionEffects;
    public static String actionBar;
    public static double lastElixirExchange;
    public static String[] bfTiers = {};

    public static String lastTier = "UNKNOWN";
    public static JsonObject potionColors;

    public static boolean movingComponents = false;

    @Override
    public void onInitialize() {
        LOGGER.info("BunnyUtils is initializing!");
        MidnightConfig.init("BunnyUtils", Config.class);

        ClientPlayConnectionEvents.JOIN.register(new JoinServerEvent());

        HudManager manager = HudManager.getInstance();
        manager.renderComponent(new TierProgressComponent());
        manager.renderComponent(new BunnyBundleComponent());
        manager.renderComponent(new PotionEffectComponent());

        new SocketHandler();

        initTiers();
        initPotionColors();
        LOGGER.info("BunnyUtils finished initializing!");
    }

    public static void initTiers(){

        URI uri;
        HttpResponse<String> httpResponse;
        try {
            uri = new URI("https://raw.githubusercontent.com/MrSam7K/BU-Data/main/tiers.txt");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).build();
            httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String tierData = httpResponse.body().replace("\n", "");
        lastTier = tierData.split("-")[1];
        bfTiers = tierData.split("-")[0].split(",");
        System.out.println("Last Tier: " + lastTier + "\n\nbfTiers: " + Arrays.toString(bfTiers));
    }

    public static void initPotionColors(){

        URI uri;
        HttpResponse<String> httpResponse;
        try {
            uri = new URI("https://raw.githubusercontent.com/MrSam7K/BU-Data/main/potions.json");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri).build();
            httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            potionColors = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
