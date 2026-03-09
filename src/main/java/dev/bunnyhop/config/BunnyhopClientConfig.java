package dev.bunnyhop.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class BunnyhopClientConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_BUNNYHOP;
    public static final ForgeConfigSpec.BooleanValue SCROLL_DOWN_JUMP;
    public static final ForgeConfigSpec.DoubleValue STRAFE_SMOOTHING;

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
                .defineInRange("strafeSmoothing", 0.18D, 0.0D, 0.95D);
        builder.pop();

        SPEC = builder.build();
    }

    private BunnyhopClientConfig() {
    }
}
