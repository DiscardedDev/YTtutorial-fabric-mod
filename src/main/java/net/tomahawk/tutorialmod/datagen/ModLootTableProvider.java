package net.tomahawk.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.tomahawk.tutorialmod.TutorialMod;
import net.tomahawk.tutorialmod.block.ModBlocks;
import net.tomahawk.tutorialmod.item.ModItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider {


    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.MOSSYPLANKS, MossyDrops(ModBlocks.MOSSYPLANKS, Items.OAK_PLANKS));

    }

    public LootTable.Builder MossyDrops(Block Block, Item drop) {
        return BlockLootTableGenerator.dropsWithSilkTouch(
                Block,
                this.applyExplosionDecay(Block,
                        ((LeafEntry.Builder<?>)
                        ItemEntry.builder(ModItems.MOSS)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 10.0F))))
                ))
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(
                                        this.applyExplosionDecay(
                                                Block, ItemEntry.builder(drop)
                                        )
                ));

    }
}
