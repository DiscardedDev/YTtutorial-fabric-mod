package net.tomahawk.tutorialmod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tomahawk.tutorialmod.TutorialMod;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceBucketMixin {

	private static boolean milkDetected = false;
	@Inject(at = @At("RETURN"), method = "tick")
	private static void init(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
		if(milkDetected && blockEntity.getStack(0).isEmpty()){
			TutorialMod.LOGGER.info("RUNNING FURNACE MIXIN FOR " + TutorialMod.MOD_ID);
			blockEntity.setStack(0, Items.BUCKET.getDefaultStack());
			milkDetected = false;
		}
		else if(!milkDetected && blockEntity.getStack(0).isOf(Items.MILK_BUCKET)){
			TutorialMod.LOGGER.info("Milk Detected!");
			milkDetected = true;
		}
	}
}