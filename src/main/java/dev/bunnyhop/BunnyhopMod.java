package dev.bunnyhop;

import com.mojang.logging.LogUtils;
import dev.bunnyhop.config.BunnyhopClientConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(BunnyhopMod.MOD_ID)
public class BunnyhopMod {
    public static final String MOD_ID = "bunnyhop";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BunnyhopMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BunnyhopClientConfig.SPEC);
        LOGGER.info("Bunnyhop Forge movement core loaded");
    }
}
