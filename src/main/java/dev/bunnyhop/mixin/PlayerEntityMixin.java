package dev.bunnyhop.mixin;

import dev.bunnyhop.movement.BunnyhopMovementController;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "travel", at = @At("HEAD"))
    private void bunnyhop$applyCustomAcceleration(Vec3d movementInput, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        Vec3d customVelocity = BunnyhopMovementController.apply(player, movementInput);
        player.setVelocity(customVelocity);
    }

    @Inject(method = "move", at = @At("HEAD"))
    private void bunnyhop$hookCollisionPhase(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        // Entry point for replacing block collision response with Source-like clipping rules.
        // Keep this hook lightweight in the base project so expanded physics logic can be added safely.
    }
}
