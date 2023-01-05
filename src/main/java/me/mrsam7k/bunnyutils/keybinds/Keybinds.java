package me.mrsam7k.bunnyutils.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.ui.ProfileViewerUI;
import me.mrsam7k.bunnyutils.util.ChatType;
import me.mrsam7k.bunnyutils.util.ChatUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class Keybinds implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Open Profile Viewer
        KeyMapping open_pv = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.bunnyutils.open_pv", InputConstants.Type.KEYSYM, -1, "bunnyutils"));

        // Open Config
        KeyMapping open_config = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.bunnyutils.open_config", InputConstants.Type.KEYSYM, -1, "bunnyutils"));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Open Profile Viewer
            while (open_pv.consumeClick()) {
                if (Config.apiKey.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
                    ProfileViewerUI pv = new ProfileViewerUI();
                    pv.scheduleOpenGui(pv, Minecraft.getInstance().player.getName().getString());
                } else {
                    ChatUtil.displayMessage("Your API key is invalid, please update it.", ChatType.ERROR);
                }
            }

            // Open Config
            while (open_config.consumeClick()){
                Minecraft mc = Minecraft.getInstance();
                mc.setScreen(Config.getScreen(Minecraft.getInstance().screen));
            }
        });
    }
}
