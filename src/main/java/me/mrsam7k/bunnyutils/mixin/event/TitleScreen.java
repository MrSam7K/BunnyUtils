package me.mrsam7k.bunnyutils.mixin.event;


import me.mrsam7k.bunnyutils.config.ITranslatable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.screens.TitleScreen.class)
public class TitleScreen extends Screen implements ITranslatable {

    protected TitleScreen(Component component) {
        super(component);
    }

    @Inject(at = @At("RETURN"), method = "createNormalMenuOptions")
    private void addBfButton(int i, int j, CallbackInfo ci) {
        final ResourceLocation bfButton = new ResourceLocation("bunnyutils:textures/bfbutton.png");

        this.addRenderableWidget(new ImageButton(this.width / 2 - 100 + 205, i, 20, 20, 0, 106, 20, bfButton, 256, 256, (button) -> {
            String address = "mcbunnyfarm.org:25565";
            ServerData serverInfo = new ServerData("BF", address, false);
            ConnectScreen.startConnecting(Minecraft.getInstance().screen, Minecraft.getInstance(), ServerAddress.parseString(address), serverInfo);
        }));
    }

}
