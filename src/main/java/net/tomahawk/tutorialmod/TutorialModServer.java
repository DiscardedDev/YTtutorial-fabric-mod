package net.tomahawk.tutorialmod;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.tomahawk.tutorialmod.TutorialMod.MOD_ID;

public class TutorialModServer implements DedicatedServerModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeServer() {
        LOGGER.info("Initilizing fabric server");
    }
}
