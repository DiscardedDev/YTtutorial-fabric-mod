package net.tomahawk.tutorialmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.TutorialMod;

public class ModItems {

    public static final Item FIRSTITEM = registerItem("firstitem", new Item(new FabricItemSettings())); //creating our first item, "name" is item ID so must be lowercase
    public static final Item SECONDITEM = registerItem("seconditem", new Item(new FabricItemSettings()));
    public static final Item THIRDITEM = registerItem("thirditem", new Item(new FabricItemSettings()));

    private static void addItemsToItemGroup(FabricItemGroupEntries entries){
        entries.add(FIRSTITEM);
    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item); //code to register item within the registry in minecraft
    }

    public static void registerItems(){
        TutorialMod.LOGGER.info("registering Mod Items for " + TutorialMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::addItemsToItemGroup); //adding the item to an item group so it appears in the Creative Tab
    }
}
