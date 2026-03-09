package dev.bunnyhop.movement;

public final class BunnyhopMovementConfig {
    private BunnyhopMovementConfig() {
    }

    public static final float GROUND_ACCELERATION = 0.12F;
    public static final float AIR_ACCELERATION = 0.035F;
    public static final float STRAFE_BOOST = 1.22F;
    public static final float MOMENTUM_RETENTION = 0.985F;
    public static final float JUMP_CONTROL = 0.08F;
    public static final float SHIFT_IN_AIR_MULTIPLIER = 1.0F;
    public static final float MAX_AIR_SPEED = 1.85F;
}
