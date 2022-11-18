package me.mrsam7k.bunnyutils.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.util.List;
import java.util.Objects;

public class TextUtil {

    public static String textComponentToColorCodes(Component message) {
        List<Component> siblings = message.getSiblings();

        StringBuilder newMsg = new StringBuilder();
        String currentText = "";

        translateSibling(message, newMsg, currentText);
        for (Component sibling : siblings) {
            translateSibling(sibling, newMsg, currentText);
            for (Component sibling2 : sibling.getSiblings()) {
                translateSibling(sibling2, newMsg, currentText);
                for (Component sibling3 : sibling2.getSiblings()) {
                    translateSibling(sibling3, newMsg, currentText);
                }
            }
        }

        return newMsg.toString();
    }

    private static void translateSibling(Component sibling, StringBuilder newMsg, String currentText) {
        Style style = sibling.getStyle();

        // color
        TextColor color = style.getColor();
        if (color == null && sibling.getSiblings().size() > 0) {
            return;
        }

        String code = MinecraftColors.getMcFromFormatting(color);
        currentText = Objects.requireNonNullElseGet(code, () -> MinecraftColors.hexToMc(String.valueOf(color)));

        if (style.isBold()) {
            currentText += "§l";
        }
        if (style.isItalic()) {
            currentText += "§o";
        }
        if (style.isStrikethrough()) {
            currentText += "§m";
        }
        if (style.isUnderlined()) {
            currentText += "§n";
        }
        if (style.isObfuscated()) {
            currentText += "§k";
        }

        currentText += sibling.getString();
        newMsg.append(currentText);
    }
}
