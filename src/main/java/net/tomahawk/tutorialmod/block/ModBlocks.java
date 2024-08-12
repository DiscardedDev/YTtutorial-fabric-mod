package net.tomahawk.tutorialmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.TutorialMod;

public class ModBlocks {

    public static final Block MOSSYPLANKS = registerBlock("mossyplanks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS))); //"FabricBlockSettings" has a bunch of info about how a block behaves when interacted with

    public static Block registerBlock(String name, Block block) { //register both the block item and the block itself
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) { //Blocks in minecraft require a "block item" to be held in a players hand,
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
        TutorialMod.LOGGER.info("Registering ModBlocks for " + TutorialMod.MOD_ID);
    }
}
