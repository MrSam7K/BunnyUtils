package me.mrsam7k.bunnyutils.mixin.event;

import net.minecraft.client.server.IntegratedPlayerList;
import org.spongepowered.asm.mixin.Mixin;




@Mixin(IntegratedPlayerList.class)
public class TablistUpdate {

    //@Inject(at = @At("TAIL"), method = "setFooter")
    private static void onUpdate() {
        System.out.println(IntegratedPlayerList.OPLIST_FILE);

    }
}
