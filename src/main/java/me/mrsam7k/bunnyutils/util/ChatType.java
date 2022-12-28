package me.mrsam7k.bunnyutils.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum ChatType {

    SUCCESS("&2‣&a ", null),
    INFO("&9‣&b ", null),
    ERROR("&4‣&c ", SoundEvents.NOTE_BLOCK_DIDGERIDOO),
    BLANK("", null);

    public final String prefix;
    public final SoundEvent sound;

    ChatType(String prefix, SoundEvent sound){
        this.prefix = prefix.replace('&', '§');
        this.sound = sound;
    }
}
