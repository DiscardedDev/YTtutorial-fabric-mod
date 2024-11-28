package net.tomahawk.tutorialmod.item.customitems;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent HAM_AND_CHEESE = new FoodComponent.Builder().hunger(8).saturationModifier(0.5f).meat().build();
    public static final FoodComponent CHEESE = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build();
    public static final FoodComponent CHEESESLICE = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).snack().build();
}
