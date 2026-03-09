package dev.bunnyhop.forge.client;

import dev.bunnyhop.config.BunnyhopClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static dev.bunnyhop.BunnyhopMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BunnyhopClientEvents {
    private static boolean keepJumpPressed;

    private BunnyhopClientEvents() {
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        if (!BunnyhopClientConfig.ENABLE_BUNNYHOP.get() || !BunnyhopClientConfig.SCROLL_DOWN_JUMP.get()) {
            return;
        }
        if (event.getScrollDelta() >= 0) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.screen != null || player.isSpectator()) {
            return;
        }

        minecraft.options.keyJump.setDown(true);
        keepJumpPressed = true;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !keepJumpPressed) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        minecraft.options.keyJump.setDown(false);
        keepJumpPressed = false;
    }
}
