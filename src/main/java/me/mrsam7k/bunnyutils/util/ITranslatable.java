package me.mrsam7k.bunnyutils.util;

import net.minecraft.network.chat.Component;

public interface ITranslatable {
    default Component getTranslation(String key) {
        return ITranslatable.get(key);
    }

    static Component get(String key) {
        return Component.translatable(key);
    }
}
