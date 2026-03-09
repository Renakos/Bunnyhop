package dev.bunnyhop.movement;

public final class BunnyhopMovementConfig {
    private BunnyhopMovementConfig() {
    }

    public static final float GROUND_ACCELERATION = 0.10F;
    public static final float AIR_ACCELERATION = 0.045F;
    public static final float AIR_CONTROL = 0.30F;
    public static final float GROUND_FRICTION = 0.91F;
    public static final float STRAFE_BOOST = 1.04F;
    public static final float MOMENTUM_RETENTION = 0.992F;
    public static final float SHIFT_IN_AIR_MULTIPLIER = 1.0F;
    public static final float MAX_AIR_SPEED = 0.85F;
    public static final float MAX_GROUND_SPEED = 0.32F;
}
