package net.tomahawk.tutorialmod.item.customitems.arrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tomahawk.tutorialmod.item.customitems.enums.AbilityBowType;



public class AbilityArrowItem extends ArrowItem {
    public AbilityArrowItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, AbilityBowType type) {

        AbilityArrowEntity abilityArrowEntity = new AbilityArrowEntity(world, shooter, type);
        abilityArrowEntity.initFromStack(stack);
        return abilityArrowEntity;
    }
}
