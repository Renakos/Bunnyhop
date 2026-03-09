package dev.bunnyhop.forge;

import dev.bunnyhop.movement.BunnyhopMovementController;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static dev.bunnyhop.BunnyhopMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public final class BunnyhopMovementEvents {
    private BunnyhopMovementEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) {
            return;
        }

        Player player = event.player;
        Vec3 input = new Vec3(player.xxa, 0.0, player.zza);
        Vec3 velocity = BunnyhopMovementController.apply(player, input);
        player.setDeltaMovement(velocity);
        player.hasImpulse = true;
    }
}
