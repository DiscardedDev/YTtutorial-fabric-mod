package net.tomahawk.tutorialmod.item.customitems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.tomahawk.tutorialmod.item.customitems.arrows.AbilityArrowItem;
import net.tomahawk.tutorialmod.item.customitems.enums.AbilityBowType;

import java.util.function.Predicate;

public class AbilityBowItem extends RangedWeaponItem implements Vanishable{
public static final int TICKS_PER_SECOND = 20;
public final int RANGE;
public final AbilityBowType type;


public AbilityBowItem(Item.Settings settings, int RANGE, AbilityBowType type) {
    super(settings);
    this.RANGE = RANGE;
    this.type = type;
}


// stack = Ability Bow
@Override
public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
    if (user instanceof PlayerEntity playerEntity) { // Is User a Player? (this method is also called by Entities who use crossbows, i.e. Skeletons)
        // boolean for if player is in creative or has infinity enchantment
        boolean HasInfiniteArrows = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        //get arrows for type of bow
        ItemStack itemStack = playerEntity.getProjectileType(stack);

        //does the player have arrows? or required to have arrows? (Infinite arrows or creative)
        if (!itemStack.isEmpty() || HasInfiniteArrows) {
            //if HasInfiniteArrows but no arrows in inventory, give arrow
            if (itemStack.isEmpty()) {
                itemStack = new ItemStack((ItemConvertible) this.getProjectiles());
            }

            //amount of time bow was drawn back for
            int ticksUsed = this.getMaxUseTime(stack) - remainingUseTicks;
            float f = getPullProgress(ticksUsed); //returns value between 0f and 1f (guessed)

            //if bow was not instantly cancelled after being pulled
            if (!((double)f < 0.1)) {
                boolean bl2 = HasInfiniteArrows && itemStack.isOf(Items.ARROW);

                //spawn arrow on server side and apply any enchantments necessary (this is the part we care about, since we will be spawning our own arrows)
                if (!world.isClient) {
                    //here we get the arrow item and start spawning the entity equivalent using the method in the arrow class
                    //in our ability bow we are adding a parameter to the createArrow of what type of bow we are.
                    AbilityArrowItem abilityArrowItem = (AbilityArrowItem) (itemStack.getItem() instanceof AbilityArrowItem ? itemStack.getItem() : Items.ARROW);
                    PersistentProjectileEntity persistentProjectileEntity = abilityArrowItem.createArrow(world, itemStack, playerEntity, type);


                    persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);

                    //if bow was drawn back fully, make shot "critical"
                    if (f == 1.0F) {
                        persistentProjectileEntity.setCritical(true);
                    }

                    //if bow has 'power' enchantment, apply to arrow
                    int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double)j * 0.5 + 0.5);
                    }

                    //if bow has 'punch' enchantment, apply to arrow
                    int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if (k > 0) {
                        persistentProjectileEntity.setPunch(k);
                    }

                    //if bow has 'flame' enchantment, apply to arrow
                    if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                        persistentProjectileEntity.setOnFireFor(100);
                    }
                    //apply damage to held item (Bow) after use, and break if necessary
                    stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(playerEntity.getActiveHand()));

                    if (bl2 || playerEntity.getAbilities().creativeMode && (itemStack.isOf(Items.SPECTRAL_ARROW) || itemStack.isOf(Items.TIPPED_ARROW))) {
                        persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    }
                    //finally, spawn the arrow
                    world.spawnEntity(persistentProjectileEntity);
                }

                //play sound as arrow is shot
                world.playSound(
                        null,
                        playerEntity.getX(),
                        playerEntity.getY(),
                        playerEntity.getZ(),
                        SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                );

                //remove arrow if player is in survival mode and arrow is normal arrow
                if (!bl2 && !playerEntity.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                    if (itemStack.isEmpty()) {
                        playerEntity.getInventory().removeOne(itemStack);
                    }
                }

                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }
    }
}

//MATH????????? I guess it calculates the amount of time the bow was drawn back and returns a value between 0f and 1f?
public static float getPullProgress(int useTicks) {
    float f = (float)useTicks / 20.0F;
    f = (f * f + f * 2.0F) / 3.0F;
    if (f > 1.0F) {
        f = 1.0F;
    }

    return f;
}

@Override
public int getMaxUseTime(ItemStack stack) {
    return 72000;
}

@Override
public UseAction getUseAction(ItemStack stack) {
    return UseAction.BOW;
}

@Override
public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ItemStack itemStack = user.getStackInHand(hand);
    boolean bl = !user.getProjectileType(itemStack).isEmpty();
    if (!user.getAbilities().creativeMode && !bl) {
        return TypedActionResult.fail(itemStack);
    } else {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
}

@Override
public Predicate<ItemStack> getProjectiles() {
    return BOW_PROJECTILES;
}

@Override
public int getRange() {
    return this.RANGE;
}
}