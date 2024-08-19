package net.tomahawk.tutorialmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.tomahawk.tutorialmod.TutorialMod;

public class CopperArmorMaterial implements ArmorMaterial {
    @Override
    public int getDurability(ArmorItem.Type type){
        // Replace this multiplier by a constant value for the durability of the armor.
        // For reference, diamond uses 33 for all armor pieces, whilst leather uses 5.
        int DURABILITY_MULTIPLIER = 11;
        return switch (type) {
            case BOOTS -> 13 * DURABILITY_MULTIPLIER;
            case LEGGINGS -> 15 * DURABILITY_MULTIPLIER;
            case CHESTPLATE -> 16 * DURABILITY_MULTIPLIER;
            case HELMET -> 11 * DURABILITY_MULTIPLIER;
            default -> 0;
        };
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        // Protection values for all the slots.
        // For reference, diamond uses 3 for boots, 6 for leggings, 8 for chestplate, and 3 for helmet,
        // whilst leather uses 1, 2, 3 and 1 respectively.
        return switch (type) {
            case BOOTS, HELMET -> 3;
            case LEGGINGS -> 4;
            case CHESTPLATE -> 6;
            default -> 0;
        };
    }

    @Override
    public int getEnchantability() {
        return 4;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }

    @Override
    public String getName() {
        return TutorialMod.MOD_ID + ":copper";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

    public static final CopperArmorMaterial INSTANCE = new CopperArmorMaterial();
}
