package dev.bunnyhop.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class BunnyhopClientConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_BUNNYHOP;
    public static final ForgeConfigSpec.BooleanValue SCROLL_DOWN_JUMP;

    public static final ForgeConfigSpec.DoubleValue STRAFE_SMOOTHING;
    public static final ForgeConfigSpec.DoubleValue GROUND_ACCELERATION;
    public static final ForgeConfigSpec.DoubleValue AIR_ACCELERATION;
    public static final ForgeConfigSpec.DoubleValue AIR_CONTROL;
    public static final ForgeConfigSpec.DoubleValue GROUND_FRICTION;
    public static final ForgeConfigSpec.DoubleValue MOMENTUM_RETENTION;
    public static final ForgeConfigSpec.DoubleValue MAX_AIR_SPEED;
    public static final ForgeConfigSpec.DoubleValue MAX_GROUND_SPEED;

    private static final boolean DEFAULT_ENABLE_BUNNYHOP = true;
    private static final boolean DEFAULT_SCROLL_DOWN_JUMP = true;
    private static final double DEFAULT_STRAFE_SMOOTHING = 0.18D;
    private static final double DEFAULT_GROUND_ACCELERATION = 0.11D;
    private static final double DEFAULT_AIR_ACCELERATION = 0.06D;
    private static final double DEFAULT_AIR_CONTROL = 0.18D;
    private static final double DEFAULT_GROUND_FRICTION = 0.90D;
    private static final double DEFAULT_MOMENTUM_RETENTION = 0.994D;
    private static final double DEFAULT_MAX_AIR_SPEED = 0.85D;
    private static final double DEFAULT_MAX_GROUND_SPEED = 0.30D;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("movement");
        ENABLE_BUNNYHOP = builder
                .comment("Enable custom bunnyhop movement logic")
                .define("enableBunnyhop", DEFAULT_ENABLE_BUNNYHOP);
        SCROLL_DOWN_JUMP = builder
                .comment("Use mouse wheel down as jump trigger")
                .define("scrollDownJump", DEFAULT_SCROLL_DOWN_JUMP);
        STRAFE_SMOOTHING = builder
                .comment("Input smoothing for strafe direction in air. 0.0 = instant, 1.0 = very slow")
                .defineInRange("strafeSmoothing", DEFAULT_STRAFE_SMOOTHING, 0.0D, 0.95D);

        GROUND_ACCELERATION = builder
                .comment("Ground acceleration per tick")
                .defineInRange("groundAcceleration", DEFAULT_GROUND_ACCELERATION, 0.0D, 1.0D);
        AIR_ACCELERATION = builder
                .comment("Air acceleration per tick")
                .defineInRange("airAcceleration", DEFAULT_AIR_ACCELERATION, 0.0D, 1.0D);
        AIR_CONTROL = builder
                .comment("Air control strength while steering")
                .defineInRange("airControl", DEFAULT_AIR_CONTROL, 0.0D, 1.0D);
        GROUND_FRICTION = builder
                .comment("Horizontal friction on ground")
                .defineInRange("groundFriction", DEFAULT_GROUND_FRICTION, 0.5D, 1.0D);
        MOMENTUM_RETENTION = builder
                .comment("How much horizontal speed is retained in air")
                .defineInRange("momentumRetention", DEFAULT_MOMENTUM_RETENTION, 0.5D, 1.0D);
        MAX_AIR_SPEED = builder
                .comment("Max horizontal speed gain from air acceleration")
                .defineInRange("maxAirSpeed", DEFAULT_MAX_AIR_SPEED, 0.05D, 2.0D);
        MAX_GROUND_SPEED = builder
                .comment("Max horizontal speed gain from ground acceleration")
                .defineInRange("maxGroundSpeed", DEFAULT_MAX_GROUND_SPEED, 0.05D, 2.0D);
        builder.pop();

        SPEC = builder.build();
    }

    public static void resetDefaults() {
        ENABLE_BUNNYHOP.set(DEFAULT_ENABLE_BUNNYHOP);
        SCROLL_DOWN_JUMP.set(DEFAULT_SCROLL_DOWN_JUMP);
        STRAFE_SMOOTHING.set(DEFAULT_STRAFE_SMOOTHING);
        GROUND_ACCELERATION.set(DEFAULT_GROUND_ACCELERATION);
        AIR_ACCELERATION.set(DEFAULT_AIR_ACCELERATION);
        AIR_CONTROL.set(DEFAULT_AIR_CONTROL);
        GROUND_FRICTION.set(DEFAULT_GROUND_FRICTION);
        MOMENTUM_RETENTION.set(DEFAULT_MOMENTUM_RETENTION);
        MAX_AIR_SPEED.set(DEFAULT_MAX_AIR_SPEED);
        MAX_GROUND_SPEED.set(DEFAULT_MAX_GROUND_SPEED);
    }

    private BunnyhopClientConfig() {
    }
}
