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
        if (!BunnyhopClientConfig.ENABLE_BUNNYHOP.get() || shouldSkip(player)) {
            return player.getDeltaMovement();
        }

        Vec3 velocity = player.getDeltaMovement();
        Vec3 localInput = smoothInput(player, new Vec3(input.x, 0.0, input.z));
        float inputMagnitude = (float) localInput.length();

        if (inputMagnitude <= 0.0001F) {
            if (player.onGround()) {
                return scaleHorizontal(velocity, BunnyhopClientConfig.GROUND_FRICTION.get());
            }
            return scaleHorizontal(velocity, BunnyhopClientConfig.MOMENTUM_RETENTION.get());
        }

        Vec3 wishDir = rotateInputToWorld(player, localInput).normalize();

        if (player.onGround()) {
            velocity = scaleHorizontal(velocity, BunnyhopClientConfig.GROUND_FRICTION.get());
            velocity = accelerate(
                    velocity,
                    wishDir,
                    BunnyhopClientConfig.MAX_GROUND_SPEED.get(),
                    BunnyhopClientConfig.GROUND_ACCELERATION.get() * inputMagnitude
            );
            return velocity;
        }

        velocity = accelerate(
                velocity,
                wishDir,
                BunnyhopClientConfig.MAX_AIR_SPEED.get(),
                BunnyhopClientConfig.AIR_ACCELERATION.get() * inputMagnitude
        );

        velocity = applyAirControl(velocity, wishDir, inputMagnitude, BunnyhopClientConfig.AIR_CONTROL.get());
        velocity = preserveForwardMomentum(velocity, player);
        return scaleHorizontal(velocity, BunnyhopClientConfig.MOMENTUM_RETENTION.get());
    }

    private static boolean shouldSkip(Player player) {
        return player.isInWater() || player.isInLava() || player.isFallFlying() || player.getAbilities().flying || player.isPassenger();
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

    private static Vec3 accelerate(Vec3 velocity, Vec3 wishDir, double maxSpeed, double accel) {
        double currentSpeed = velocity.x * wishDir.x + velocity.z * wishDir.z;
        double addSpeed = maxSpeed - currentSpeed;
        if (addSpeed <= 0.0) {
            return velocity;
        }

        double accelSpeed = Math.min(addSpeed, accel);
        return velocity.add(wishDir.x * accelSpeed, 0.0, wishDir.z * accelSpeed);
    }

    private static Vec3 applyAirControl(Vec3 velocity, Vec3 wishDir, float inputMagnitude, double airControl) {
        if (inputMagnitude <= 0.0001F) {
            return velocity;
        }

        Vec3 horizontal = new Vec3(velocity.x, 0.0, velocity.z);
        double speed = horizontal.length();
        if (speed < 0.0001D) {
            return velocity;
        }

        Vec3 currentDir = horizontal.scale(1.0 / speed);
        Vec3 blended = currentDir.lerp(wishDir, airControl * inputMagnitude).normalize();

        return new Vec3(blended.x * speed, velocity.y, blended.z * speed);
    }

    private static Vec3 preserveForwardMomentum(Vec3 velocity, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0, look.z);
        if (forward.lengthSqr() < 0.0001D) {
            return velocity;
        }

        forward = forward.normalize();
        double forwardSpeed = velocity.x * forward.x + velocity.z * forward.z;
        if (forwardSpeed <= 0.0D) {
            return velocity;
        }

        double clampedForward = Math.max(forwardSpeed * 0.85D, forwardSpeed - 0.02D);
        Vec3 lateral = new Vec3(velocity.x, 0.0, velocity.z).subtract(forward.scale(forwardSpeed));
        Vec3 combined = forward.scale(clampedForward).add(lateral);
        return new Vec3(combined.x, velocity.y, combined.z);
    }

    private static Vec3 scaleHorizontal(Vec3 velocity, double scale) {
        return new Vec3(velocity.x * scale, velocity.y, velocity.z * scale);
    }
}
