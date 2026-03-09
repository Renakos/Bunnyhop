package dev.bunnyhop;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(BunnyhopMod.MOD_ID)
public class BunnyhopMod {
    public static final String MOD_ID = "bunnyhop";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BunnyhopMod() {
        LOGGER.info("Bunnyhop Forge movement core loaded");
    }
}
            