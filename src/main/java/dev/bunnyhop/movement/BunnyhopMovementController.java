package dev.bunnyhop.movement;

import dev.bunnyhop.config.BunnyhopClientConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class BunnyhopMovementController {
    private static final Map<UUID, Vec3> SMOOTHED_INPUTS = new ConcurrentHashMap<>();

    private BunnyhopMovementController() {
    }

    public static Vec3 apply(Player player, Vec3 input) {
        if (!BunnyhopClientConfig.ENABLE_BUNNYHOP.get()) {
            return player.getDeltaMovement();
        }

        Vec3 velocity = player.getDeltaMovement();
        Vec3 localInput = smoothInput(player, new Vec3(input.x, 0.0, input.z));
        float rawMagnitude = Mth.sqrt((float) (localInput.x * localInput.x + localInput.z * localInput.z));

        if (rawMagnitude <= 0.0001F) {
            if (player.onGround()) {
                return new Vec3(velocity.x * BunnyhopMovementConfig.GROUND_FRICTION, velocity.y, velocity.z * BunnyhopMovementConfig.GROUND_FRICTION);
            }
            return new Vec3(velocity.x * BunnyhopMovementConfig.MOMENTUM_RETENTION, velocity.y, velocity.z * BunnyhopMovementConfig.MOMENTUM_RETENTION);
        }

        Vec3 wishDir = rotateInputToWorld(player, localInput).normalize();

        if (player.onGround()) {
            velocity = new Vec3(velocity.x * BunnyhopMovementConfig.GROUND_FRICTION, velocity.y, velocity.z * BunnyhopMovementConfig.GROUND_FRICTION);
            velocity = accelerate(velocity, wishDir, BunnyhopMovementConfig.MAX_GROUND_SPEED, BunnyhopMovementConfig.GROUND_ACCELERATION);
            if (Math.abs(input.x) > 0.01F && Math.abs(input.z) > 0.01F) {
                velocity = new Vec3(velocity.x * BunnyhopMovementConfig.STRAFE_BOOST, velocity.y, velocity.z * BunnyhopMovementConfig.STRAFE_BOOST);
            }
            return velocity;
        }

        float airAccel = BunnyhopMovementConfig.AIR_ACCELERATION;
        if (player.isShiftKeyDown()) {
            airAccel *= BunnyhopMovementConfig.SHIFT_IN_AIR_MULTIPLIER;
        }

        velocity = accelerate(velocity, wishDir, BunnyhopMovementConfig.MAX_AIR_SPEED, airAccel);
        velocity = applyAirControl(velocity, wishDir, rawMagnitude);
        return new Vec3(velocity.x * BunnyhopMovementConfig.MOMENTUM_RETENTION, velocity.y, velocity.z * BunnyhopMovementConfig.MOMENTUM_RETENTION);
    }

    private static Vec3 smoothInput(Player player, Vec3 input) {
        UUID id = player.getUUID();
        Vec3 previous = SMOOTHED_INPUTS.getOrDefault(id, Vec3.ZERO);
        double t = BunnyhopClientConfig.STRAFE_SMOOTHING.get();
        Vec3 smoothed = previous.lerp(input, 1.0 - t);

        if (player.onGround() && input.lengthSqr() < 0.0001) {
            smoothed = Vec3.ZERO;
        }

        SMOOTHED_INPUTS.put(id, smoothed);
        return smoothed;
    }

    private static Vec3 rotateInputToWorld(Player player, Vec3 input) {
        float yawRad = player.getYRot() * ((float) Math.PI / 180F);
        float sin = Mth.sin(yawRad);
        float cos = Mth.cos(yawRad);

        double x = input.x * cos - input.z * sin;
        double z = input.z * cos + input.x * sin;
        return new Vec3(x, 0.0, z);
    }

    private static Vec3 accelerate(Vec3 velocity, Vec3 wishDir, float maxSpeed, float accel) {
        double currentSpeed = velocity.x * wishDir.x + velocity.z * wishDir.z;
        double addSpeed = maxSpeed - currentSpeed;
        if (addSpeed <= 0.0) {
            return velocity;
        }

        double accelSpeed = Math.min(addSpeed, accel);
        return velocity.add(wishDir.x * accelSpeed, 0.0, wishDir.z * accelSpeed);
    }

    private static Vec3 applyAirControl(Vec3 velocity, Vec3 wishDir, float inputMagnitude) {
        if (inputMagnitude <= 0.0001F) {
            return velocity;
        }

        double lateralSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        if (lateralSpeed < 0.0001D) {
            return velocity;
        }

        double dot = (velocity.x * wishDir.x + velocity.z * wishDir.z) / lateralSpeed;
        double control = BunnyhopMovementConfig.AIR_CONTROL * dot * dot;

        return new Vec3(
                velocity.x + wishDir.x * control,
                velocity.y,
                velocity.z + wishDir.z * control
        );
    }
}
