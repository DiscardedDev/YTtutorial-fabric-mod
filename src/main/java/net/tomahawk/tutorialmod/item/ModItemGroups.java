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

public class ModItemGroups {
    public static final ItemGroup SANDWITCHES = Registry.register(Registries.ITEM_GROUP, new Identifier(TutorialMod.MOD_ID, "sandwiches"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.sandwiches"))
                    .icon(() -> new ItemStack(Items.BREAD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.FIRSTITEM);
                        entries.add(ModItems.SECONDITEM);
                        entries.add(ModItems.THIRDITEM);

                    }).build());

    public static void registerItemGroups() {
        TutorialMod.LOGGER.info("Registering Item Groups for " + TutorialMod.MOD_ID);
    }
}
