package me.mrsam7k.bunnyutils.discordRPC;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import me.mrsam7k.bunnyutils.Bunnyutils;
import me.mrsam7k.bunnyutils.config.Config;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

public class DiscordRPC {

    private static int updateTicks = 20;


    public static void discordRPC() throws IOException {
        File discordLibrary = DownloadNativeLibrary.downloadDiscordLibrary();
        if (discordLibrary == null) {
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }
        // Initialize the Core
        Core.init(discordLibrary);

        try {
            CreateParams params = new CreateParams();
            params.setClientID(874632541208993803L);
            params.setFlags(CreateParams.getDefaultFlags());

            //try () {
                //try () {
            Core core = new Core(params);
            Activity activity = new Activity();
                    activity.setDetails("In Slime Caves");
                    activity.setState("Recently used Phantom Arm");

                    activity.timestamps().setStart(Instant.now());

                    activity.assets().setLargeImage("large");

                    core.activityManager().updateActivity(activity);
                //}

                ClientTickEvents.START_CLIENT_TICK.register((mc) -> {
                    updateTicks++;
                    if(updateTicks > 20) {
                        updateTicks = 0;
                        try {
                            Bunnyutils.discordPresence.put("prestige", "LXIX");
                            Bunnyutils.discordPresence.put("location", "Bunny Island");
                            Bunnyutils.discordPresence.put("player", Minecraft.getInstance().player.getName().getString());
                            String firstLine = Config.dcFirstLine;
                            String secLine = Config.dcSecLine;
                            for (Map.Entry<String, String> entry : Bunnyutils.discordPresence.entrySet()) {
                                firstLine = firstLine.replace("{" + entry.getKey() + "}", entry.getValue());
                                secLine = secLine.replace("{" + entry.getKey() + "}", entry.getValue());
                            }

                            activity.setDetails(firstLine);
                            activity.setState(secLine);
                            core.activityManager().updateActivity(activity);
                            if (core.isOpen()) core.runCallbacks();

                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                });

                ClientPlayConnectionEvents.DISCONNECT.register((listener, mc) -> {
                    try {
                        core.close();
                        activity.close();
                    } catch (Exception ignored){}
                });
            //}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
