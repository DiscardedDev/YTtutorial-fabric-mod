package net.tomahawk.tutorialmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.TutorialMod;
import net.tomahawk.tutorialmod.item.custom.MetalDetectorItem;

public class ModItems {

    public static final Item MOSS = registerItem("moss", new Item(new FabricItemSettings())); //creating our first item, "name" is item ID so must be lowercase
    public static final Item METALDETECT = registerItem("metaldetect", new MetalDetectorItem(new FabricItemSettings().maxDamage(100)));

    public static final Item COPPER_HELMET = registerItem("copper_helmet", new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item COPPER_BOOTS = registerItem("copper_boots", new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.BOOTS, new Item.Settings()));
    public static final Item COPPER_LEGGINGS = registerItem("copper_leggings", new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate", new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe", new PickaxeItem(ModToolMaterial.COPPER, 3, -2.8f, new FabricItemSettings()));
    public static final Item COPPER_SWORD = registerItem("copper_sword", new SwordItem(ModToolMaterial.COPPER, 5, -2.4f, new FabricItemSettings()));
    public static final Item COPPER_AXE = registerItem("copper_axe", new AxeItem(ModToolMaterial.COPPER, 6, -3.1f, new FabricItemSettings()));
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel", new ShovelItem(ModToolMaterial.COPPER, 4, -3.0f, new FabricItemSettings()));
    public static final Item COPPER_HOE = registerItem("copper_hoe", new HoeItem(ModToolMaterial.COPPER, 1, -2.5f, new FabricItemSettings()));

    public static final Item CHEESEWEDGE = registerItem("cheesewedge", new Item(new FabricItemSettings().food(ModFoodComponents.CHEESE)));
    public static final Item CHEESESLICE = registerItem("cheeseslice", new Item(new FabricItemSettings().food(ModFoodComponents.CHEESESLICE)));
    public static final Item HAM_AND_CHEESE = registerItem("hamandcheese", new Item(new FabricItemSettings().food(ModFoodComponents.HAM_AND_CHEESE)));
    public static final Item HAMSLICE = registerItem("hamslice", new Item(new FabricItemSettings().food(ModFoodComponents.CHEESESLICE)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item); //code to register item within the registry in minecraft
    }

    public static void registerItems(){
        TutorialMod.LOGGER.info("registering Mod Items for " + TutorialMod.MOD_ID);

    }
}
