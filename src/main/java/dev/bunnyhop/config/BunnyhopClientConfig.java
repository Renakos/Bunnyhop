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

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("movement");
        ENABLE_BUNNYHOP = builder
                .comment("Enable custom bunnyhop movement logic")
                .define("enableBunnyhop", true);
        SCROLL_DOWN_JUMP = builder
                .comment("Use mouse wheel down as jump trigger")
                .define("scrollDownJump", true);
        STRAFE_SMOOTHING = builder
                .comment("Input smoothing for strafe direction in air. 0.0 = instant, 1.0 = very slow")
                .defineInRange("strafeSmoothing", 0.22D, 0.0D, 0.95D);

        GROUND_ACCELERATION = builder
                .comment("Ground acceleration per tick")
                .defineInRange("groundAcceleration", 0.055D, 0.0D, 1.0D);
        AIR_ACCELERATION = builder
                .comment("Air acceleration per tick")
                .defineInRange("airAcceleration", 0.018D, 0.0D, 1.0D);
        AIR_CONTROL = builder
                .comment("Air control strength while steering")
                .defineInRange("airControl", 0.09D, 0.0D, 1.0D);
        GROUND_FRICTION = builder
                .comment("Horizontal friction on ground")
                .defineInRange("groundFriction", 0.86D, 0.5D, 1.0D);
        MOMENTUM_RETENTION = builder
                .comment("How much horizontal speed is retained in air")
                .defineInRange("momentumRetention", 0.995D, 0.5D, 1.0D);
        MAX_AIR_SPEED = builder
                .comment("Max horizontal speed gain from air acceleration")
                .defineInRange("maxAirSpeed", 0.45D, 0.05D, 2.0D);
        MAX_GROUND_SPEED = builder
                .comment("Max horizontal speed gain from ground acceleration")
                .defineInRange("maxGroundSpeed", 0.26D, 0.05D, 2.0D);
        builder.pop();

        SPEC = builder.build();
    }

    private BunnyhopClientConfig() {
    }
}
