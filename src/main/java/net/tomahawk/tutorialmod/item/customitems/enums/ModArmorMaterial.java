package net.tomahawk.tutorialmod.item.customitems.enums;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.tomahawk.tutorialmod.TutorialMod;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
    COPPER("copper", 14, 2, 5, 4, 0,SoundEvents.ITEM_ARMOR_EQUIP_IRON,() -> Ingredient.ofItems(Items.COPPER_INGOT),0, 0);


    private final String name;
    private final int armorDurability;
    private final int handbProtection;
    private final int chestplateProtection;
    private final int leggingsProtection;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final Supplier<Ingredient> repairIngredient;
    private final float toughness;
    private final float knockbackResistance;

    ModArmorMaterial(String name, int armorDurability, int handbProtection, int chestplateProtection, int leggingsProtection, int enchantability, SoundEvent equipSound, Supplier<Ingredient> repairItem, float toughness, float knockbackResistance) {
        this.name = name;
        this.armorDurability = armorDurability;
        this.handbProtection = handbProtection;
        this.chestplateProtection = chestplateProtection;
        this.leggingsProtection = leggingsProtection;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairIngredient = repairItem;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(ArmorItem.Type type){
        // Replace this multiplier by a constant value for the durability of the armor.
        // For reference, diamond uses 33 for all armor pieces, whilst leather uses 5.
        int DURABILITY_MULTIPLIER = this.armorDurability;
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
        // For reference, diamond uses 3 for boots&helmet, 6 for leggings, 8 for chestplate
        // whilst leather uses 1, 2 & 3 respectively.
        return switch (type) {
            case BOOTS, HELMET -> this.handbProtection;
            case LEGGINGS -> this.leggingsProtection;
            case CHESTPLATE -> this.chestplateProtection;
            default -> 0;
        };
    }

    @Override
    public int getEnchantability() {
        //For reference, Iron is 9, Leather is 15, Diamond is 10
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return TutorialMod.MOD_ID + ":"+ this.name;
    }

    @Override
    public float getToughness() {
        //For reference, only diamond (2.0f) and netherite (3.0f) have toughness
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        //For reference, no base armor has knockback
        // change this value to 0.XF, where X is the level of knockback resistance you want.
        return this.knockbackResistance;
    }
}
