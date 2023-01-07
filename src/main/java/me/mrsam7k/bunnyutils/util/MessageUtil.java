package me.mrsam7k.bunnyutils.util;

import me.mrsam7k.bunnyutils.Bunnyutils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageUtil {

    public static final Pattern HYPERLINK_PATTERN = Pattern.compile("\\([\\w&§]+\\)\\[\\S+\\]");

    public static void sendMessages(String... messages) {
        for (String message : messages)
            sendMessage(message);
    }

    public static void sendMessageWithPrefix(String message) {
        sendMessage("&3[BU] &b" + message);
    }

    public static void sendMessage(String message) {
        if (HYPERLINK_PATTERN.matcher(message).find()) {
            Bunnyutils.LOGGER.info("a");
            message = message.replaceAll("&", "§");
            Matcher matcher = HYPERLINK_PATTERN.matcher(message);

            MutableComponent text = Component.empty();
            int lastEnd = 0;
            while (matcher.find()) {
                Bunnyutils.LOGGER.info("b");
                String textBeforeHyperlink = message.substring(lastEnd, matcher.start());
                String[] partsOfHyperlink = message.substring(matcher.start() + 1, matcher.end() - 1).split("\\)\\[");
                String display = partsOfHyperlink[0];
                String link = partsOfHyperlink[1];
                text = text.append(Component.literal(textBeforeHyperlink));
                MutableComponent hyperlinkText = Component.literal(display);
                if (link.startsWith("run:"))
                    hyperlinkText.setStyle(hyperlinkText.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, link.substring(4))));
                else
                    hyperlinkText.setStyle(hyperlinkText.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link)));
                text = text.append(hyperlinkText);
                lastEnd = matcher.end();
            }
            Bunnyutils.LOGGER.info("a2");
            text = text.append(Component.literal(message.substring(lastEnd)));
            Minecraft.getInstance().gui.getChat().addMessage(text);
        } else {
            Minecraft.getInstance().gui.getChat().addMessage(Component.literal(message.replaceAll("&", "§")));
        }
    }

}
