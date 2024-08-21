package net.tomahawk.tutorialmod.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceRecipeRemainderMixin {
	//inject at method "craftRecipe" at an invoke of method "decrement" on class "Item Stack"
	// therefore, getting the point at which an item is removed from the stack located at the top slot
	@Inject(method = "craftRecipe", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
	private static void turnMilkBucketIntoBucket(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> cir) {
		ItemStack inputStack = slots.get(0);
		if (inputStack.isOf(Items.MILK_BUCKET) ) {
			slots.set(0, new ItemStack(Items.BUCKET));
		}
	}
}