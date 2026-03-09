package dev.bunnyhop.movement;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class BunnyhopMovementController {
    private BunnyhopMovementController() {
    }

    public static Vec3d apply(PlayerEntity player, Vec3d input) {
        Vec3d velocity = player.getVelocity();
        float inputMagnitude = MathHelper.sqrt((float) (input.x * input.x + input.z * input.z));

        if (inputMagnitude > 0.001F) {
            float yawRad = player.getYaw() * 0.017453292F;
            float sin = MathHelper.sin(yawRad);
            float cos = MathHelper.cos(yawRad);

            double wishX = (input.x * cos - input.z * sin) / inputMagnitude;
            double wishZ = (input.z * cos + input.x * sin) / inputMagnitude;

            float accel = player.isOnGround() ? BunnyhopMovementConfig.GROUND_ACCELERATION : BunnyhopMovementConfig.AIR_ACCELERATION;
            if (!player.isOnGround() && player.isSneaking()) {
                accel *= BunnyhopMovementConfig.SHIFT_IN_AIR_MULTIPLIER;
            }

            double currentSpeed = velocity.x * wishX + velocity.z * wishZ;
            double addSpeed = Math.max(0.0, BunnyhopMovementConfig.MAX_AIR_SPEED - currentSpeed);
            double accelSpeed = Math.min(addSpeed, accel * inputMagnitude);

            velocity = velocity.add(wishX * accelSpeed, 0.0, wishZ * accelSpeed);

            if (!player.isOnGround()) {
                velocity = velocity.add(wishX * BunnyhopMovementConfig.JUMP_CONTROL * inputMagnitude, 0.0,
                        wishZ * BunnyhopMovementConfig.JUMP_CONTROL * inputMagnitude);
                velocity = new Vec3d(velocity.x * BunnyhopMovementConfig.MOMENTUM_RETENTION, velocity.y,
                        velocity.z * BunnyhopMovementConfig.MOMENTUM_RETENTION);
            } else if (Math.abs(input.x) > 0.0F && Math.abs(input.z) > 0.0F) {
                velocity = new Vec3d(velocity.x * BunnyhopMovementConfig.STRAFE_BOOST, velocity.y,
                        velocity.z * BunnyhopMovementConfig.STRAFE_BOOST);
            }
        }

        return velocity;
    }
}
