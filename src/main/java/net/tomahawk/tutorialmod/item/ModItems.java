package net.tomahawk.tutorialmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.TutorialMod;

public class ModItems {

    public static final Item MOSS = registerItem("moss", new Item(new FabricItemSettings())); //creating our first item, "name" is item ID so must be lowercase

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item); //code to register item within the registry in minecraft
    }

    public static void registerItems(){
        TutorialMod.LOGGER.info("registering Mod Items for " + TutorialMod.MOD_ID);

    }
}
