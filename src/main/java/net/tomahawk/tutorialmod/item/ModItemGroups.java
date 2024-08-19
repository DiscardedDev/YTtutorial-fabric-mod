package net.tomahawk.tutorialmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.TutorialMod;
import net.tomahawk.tutorialmod.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup ITEMS = Registry.register(Registries.ITEM_GROUP, new Identifier(TutorialMod.MOD_ID, "moditems"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.moditems"))
                    .icon(() -> new ItemStack(ModItems.METALDETECT)).entries((displayContext, entries) -> {
                        entries.add(ModItems.METALDETECT);
                        entries.add(ModItems.MOSS);
                    }).build());

    public static final ItemGroup BLOCKS = Registry.register(Registries.ITEM_GROUP, new Identifier(TutorialMod.MOD_ID, "modblocks"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.modblocks"))
                    .icon(() -> new ItemStack(Items.OAK_PLANKS)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.MOSSYPLANKS);
                    }).build());

    public static final ItemGroup FOOD = Registry.register(Registries.ITEM_GROUP, new Identifier(TutorialMod.MOD_ID, "modfood"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.modfood"))
                    .icon(() -> new ItemStack(Items.BREAD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.HAM_AND_CHEESE);
                        entries.add(ModItems.CHEESESLICE);
                        entries.add(ModItems.CHEESEWEDGE);
                        entries.add(ModItems.HAMSLICE);
                    }).build());

    public static final ItemGroup COMBAT = Registry.register(Registries.ITEM_GROUP, new Identifier(TutorialMod.MOD_ID, "modcombat"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.modcombat"))
                    .icon(() -> new ItemStack(ModItems.COPPER_HELMET)).entries((displayContext, entries) -> {
                        entries.add(ModItems.COPPER_HELMET);
                        entries.add(ModItems.COPPER_CHESTPLATE);
                        entries.add(ModItems.COPPER_LEGGINGS);
                        entries.add(ModItems.COPPER_BOOTS);
                    }).build());
    public static void registerItemGroups() {
        TutorialMod.LOGGER.info("Registering Item Groups for " + TutorialMod.MOD_ID);
    }
}
