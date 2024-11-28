package net.tomahawk.tutorialmod.item.customitems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.tomahawk.tutorialmod.TutorialMod;
import net.tomahawk.tutorialmod.item.ModItems;
import net.tomahawk.tutorialmod.item.customitems.arrows.AbilityArrowItem;
import net.tomahawk.tutorialmod.item.customitems.enums.AbilityBowType;
import net.tomahawk.tutorialmod.util.ModTags;

import java.util.function.Predicate;

public class AbilityBowItem extends RangedWeaponItem implements Vanishable{
//public static final int TICKS_PER_SECOND = 20;
private static final Predicate<ItemStack> BOW_PROJECTILES = stack -> stack.isIn(ModTags.Items.ABILITY_ARROWS);
private final int RANGE;
public final AbilityBowType type;
ItemStack bowArrow;

public AbilityBowItem(Item.Settings settings, int RANGE, AbilityBowType type) {
    super(settings);
    this.RANGE = RANGE;
    this.type = type;
    if(type == AbilityBowType.MOLTEN){
        bowArrow = Items.FIRE_CHARGE.getDefaultStack();
    }
    else {
        bowArrow = ModItems.ABILITY_ARROW.getDefaultStack();
    }
}


// stack = Ability Bow
@Override
public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
    if (user instanceof PlayerEntity playerEntity && !world.isClient) { // Is User a Player? (this method is also called by Entities who use crossbows, i.e. Skeletons)
        // boolean for if player is in creative or has infinity enchantment
        boolean HasInfiniteArrows = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        PlayerInventory inventory = playerEntity.getInventory();
        TutorialMod.LOGGER.info("attempting to shoot bow...");
        //does the player have arrows? or required to have arrows? (Infinite arrows or creative)
        if (inventory.contains(bowArrow) || HasInfiniteArrows) {
            TutorialMod.LOGGER.info("player has arrows");
            ItemStack arrowStack = bowArrow;
            if (!HasInfiniteArrows) {
                arrowStack = inventory.getStack(inventory.getSlotWithStack(bowArrow));
            }
            //amount of time bow was drawn back for
            int ticksUsed = this.getMaxUseTime(stack) - remainingUseTicks;
            float f = getPullProgress(ticksUsed); //returns value between 0f and 1f (guessed)

            //if bow was not instantly cancelled after being pulled
            if (!((double)f < 0.1)) {

                //spawn arrow on server side and apply any enchantments necessary (this is the part we care about, since we will be spawning our own arrows)

                    TutorialMod.LOGGER.info("attempting to spawn arrow...");
                    //here we get the arrow item and start spawning the entity equivalent using the method in the arrow class
                    //in our ability bow we are adding a parameter to the createArrow of what type of bow we are.
                    if (type == AbilityBowType.MOLTEN){
                        //shoot fireball in direction of where player is looking
                        double vX = Math.cos(Math.toRadians(playerEntity.getYaw() + 90.0F)) * f;
                        double vY = -Math.sin(Math.toRadians(playerEntity.getPitch())) * f;
                        double vZ = Math.sin(Math.toRadians(playerEntity.getYaw() + 90.0F)) * f;
                         //creating the fireball entity and setting its position and velocity
                        FireballEntity fireballEntity = new FireballEntity(world, user, vX, vY, vZ, 1);
                        fireballEntity.setPosition(user.getX(), user.getEyeY() - 0.1F, user.getZ());
                        world.spawnEntity(fireballEntity);
                    }
                    else {
                        AbilityArrowItem abilityArrowItem = (AbilityArrowItem) bowArrow.getItem();

                        PersistentProjectileEntity persistentProjectileEntity = abilityArrowItem.createArrow(world, playerEntity, type);

                        persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);

                        //if bow was drawn back fully, make shot "critical"
                        if (f == 1.0F) {
                            persistentProjectileEntity.setCritical(true);
                        }

                        //if bow has 'power' enchantment, apply to arrow
                        int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double) j * 0.5 + 0.5);
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
                if (!HasInfiniteArrows) {
                    //remove stack containing bowarrow from inventory
                    arrowStack.decrement(1);
                    if (arrowStack.getCount() > 0) {
                        inventory.removeOne(arrowStack);
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
    TutorialMod.LOGGER.info("Use: " + itemStack);
    boolean bl = user.getInventory().contains(bowArrow);
    if (!user.getAbilities().creativeMode && !bl) {
        TutorialMod.LOGGER.info("CANT USE: " + itemStack + "!" + " player does not have: " + bowArrow);
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