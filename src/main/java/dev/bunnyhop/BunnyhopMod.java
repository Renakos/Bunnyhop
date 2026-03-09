package dev.bunnyhop;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BunnyhopMod implements ModInitializer {
    public static final String MOD_ID = "bunnyhop";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Bunnyhop movement core loaded");
    }
}
