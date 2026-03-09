package dev.bunnyhop.movement;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class BunnyhopMovementController {
    private BunnyhopMovementController() {
    }

    public static Vec3 apply(Player player, Vec3 input) {
        Vec3 velocity = player.getDeltaMovement();
        float inputMagnitude = Mth.sqrt((float) (input.x * input.x + input.z * input.z));

        if (inputMagnitude <= 0.001F) {
            return velocity;
        }

        float yawRad = player.getYRot() * ((float) Math.PI / 180F);
        float sin = Mth.sin(yawRad);
        float cos = Mth.cos(yawRad);

        double wishX = (input.x * cos - input.z * sin) / inputMagnitude;
        double wishZ = (input.z * cos + input.x * sin) / inputMagnitude;

        float accel = player.onGround() ? BunnyhopMovementConfig.GROUND_ACCELERATION : BunnyhopMovementConfig.AIR_ACCELERATION;
        if (!player.onGround() && player.isShiftKeyDown()) {
            accel *= BunnyhopMovementConfig.SHIFT_IN_AIR_MULTIPLIER;
        }

        double currentSpeed = velocity.x * wishX + velocity.z * wishZ;
        double addSpeed = Math.max(0.0, BunnyhopMovementConfig.MAX_AIR_SPEED - currentSpeed);
        double accelSpeed = Math.min(addSpeed, accel * inputMagnitude);

        velocity = velocity.add(wishX * accelSpeed, 0.0, wishZ * accelSpeed);

        if (!player.onGround()) {
            velocity = velocity.add(
                    wishX * BunnyhopMovementConfig.JUMP_CONTROL * inputMagnitude,
                    0.0,
                    wishZ * BunnyhopMovementConfig.JUMP_CONTROL * inputMagnitude
            );
            velocity = new Vec3(
                    velocity.x * BunnyhopMovementConfig.MOMENTUM_RETENTION,
                    velocity.y,
                    velocity.z * BunnyhopMovementConfig.MOMENTUM_RETENTION
            );
        } else if (Math.abs(input.x) > 0.0F && Math.abs(input.z) > 0.0F) {
            velocity = new Vec3(
                    velocity.x * BunnyhopMovementConfig.STRAFE_BOOST,
                    velocity.y,
                    velocity.z * BunnyhopMovementConfig.STRAFE_BOOST
            );
        }

        return velocity;
    }
}
