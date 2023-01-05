package me.mrsam7k.bunnyutils.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    public static String fromTrimmed(String trimmedUUID) throws IllegalArgumentException {
        if (trimmedUUID == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder builder = new StringBuilder(trimmedUUID.trim());
        try {
            builder.insert(20, "-");
            builder.insert(16, "-");
            builder.insert(12, "-");
            builder.insert(8, "-");
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
        return builder.toString();
    }

    public static String toFancyNumber(int num){
        return NumberFormat.getInstance(Locale.US).format((Integer) num);
    }

    public static String toProperCase(String s) {
        String[] s2 = s.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String word : s2) {
            list.add(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());
        }
        return String.join(" ", list);
    }
}
