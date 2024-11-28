package net.tomahawk.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.tomahawk.tutorialmod.item.ModItems;

//Requires entrypoint in fabric.mod.json
public class TutorialModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerModelPredicateProviders();
    }

    public static void registerModelPredicateProviders() {
        ModelPredicateProviderRegistry.register(ModItems.TEST_BOW, new Identifier("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(ModItems.TEST_BOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}
