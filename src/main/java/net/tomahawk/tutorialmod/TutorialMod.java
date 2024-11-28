package net.tomahawk.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.block.ModBlocks;
import net.tomahawk.tutorialmod.item.ModItemGroups;
import net.tomahawk.tutorialmod.item.ModItems;
import net.tomahawk.tutorialmod.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerItems();

		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModLootTableModifiers.modifyLootTables();
		LOGGER.info("Hello Fabric world!");
	}


}