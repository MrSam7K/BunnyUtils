package me.mrsam7k.bunnyutils;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.event.JoinServerEvent;
import me.mrsam7k.bunnyutils.socket.SocketHandler;
import me.mrsam7k.bunnyutils.hud.HudManager;
import me.mrsam7k.bunnyutils.hud.components.BunnyBundleComponent;
import me.mrsam7k.bunnyutils.hud.components.TierProgressComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.GuiMessage;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.util.ArrayList;
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
    public static String[] bfTiers = {
            "Iron I","Iron II","Iron III","Iron IV","Iron V",
            "Gold I","Gold II","Gold III","Gold IV","Gold V",
            "Diamond I","Diamond II","Diamond III","Diamond IV","Diamond V",
            "Emerald I","Emerald II","Emerald III","Emerald IV","Emerald V",
            "Ruby I","Ruby II","Ruby III","Ruby IV","Ruby V",
            "Jr. Bunny I","Jr. Bunny II","Jr. Bunny III","Jr. Bunny IV","Jr. Bunny V",
            "Bunny I","Bunny II","Bunny III","Bunny IV","Bunny V",
            "Master ⭐","Master ⭐⭐","Master ⭐⭐⭐","Master ⭐⭐⭐⭐","Master ⭐⭐⭐⭐⭐",
            "Overlord \uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25","Overlord \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25",
            "Eminence ☀","Eminence ☀☀","Eminence ☀☀☀","Eminence ☀☀☀☀","Eminence ☀☀☀☀☀",
            "Divinity ☁","Divinity ☁☁","Divinity ☁☁☁","Divinity ☁☁☁☁","Divinity ☁☁☁☁☁",

    };

    public static boolean movingComponents = false;

    @Override
    public void onInitialize() {
        LOGGER.info("BunnyUtils is initializing!");
        MidnightConfig.init("BunnyUtils", Config.class);

        ClientPlayConnectionEvents.JOIN.register(new JoinServerEvent());

        HudManager manager = HudManager.getInstance();
        manager.renderComponent(new TierProgressComponent());
        manager.renderComponent(new BunnyBundleComponent());

        new SocketHandler();

        LOGGER.info("BunnyUtils finished initializing!");
    }
}
