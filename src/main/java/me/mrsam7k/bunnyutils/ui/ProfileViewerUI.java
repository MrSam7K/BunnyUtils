package me.mrsam7k.bunnyutils.ui;

import com.google.gson.JsonObject;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import me.mrsam7k.bunnyutils.config.Config;
import me.mrsam7k.bunnyutils.util.IMenu;
import me.mrsam7k.bunnyutils.util.ITranslatable;
import me.mrsam7k.bunnyutils.util.WebUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ProfileViewerUI extends LightweightGuiDescription implements IMenu {

    @Override
    public void open(String... args) throws IOException {
        Minecraft mc = Minecraft.getInstance();

        WPlainPanel root = new WPlainPanel();
        root.setSize(220, 220);

        addMenuItems(root);
        setRootPanel(root);
    }

    private void addMenuItems(WPlainPanel panel) {
        // ------------------------ Features Button ------------------------
        JsonObject playerData = WebUtil.getObject(
                "http://mcbunnyfarm.org/api/player");
        WText prestige = new WText(ITranslatable.get("Couldn't fetch"));
        if(playerData != null) {
            playerData.addProperty("Api-Key", Config.apiKey);
            prestige = new WText(ITranslatable.get(playerData.get("prestige").getAsString()));
        }
        panel.add(prestige, 60, 148, 100, 20);
    }

}
