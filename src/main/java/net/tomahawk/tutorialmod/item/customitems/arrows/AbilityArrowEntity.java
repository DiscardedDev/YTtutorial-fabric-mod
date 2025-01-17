package net.tomahawk.tutorialmod.item.customitems.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tomahawk.tutorialmod.item.customitems.enums.AbilityBowType;

import java.util.Random;

public class AbilityArrowEntity extends PersistentProjectileEntity {

    public final AbilityBowType type;
    public final LivingEntity owner;
    public boolean hasDoneAbility;

    public AbilityArrowEntity(World world, LivingEntity owner, AbilityBowType type) {
        super(EntityType.ARROW, owner, world);
        this.type = type;
        this.owner = owner;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }


    @Override
    public void tick() {
        super.tick();

        if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte) 0);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        World world = this.getWorld();
        BlockPos pos = blockHitResult.getBlockPos();
        //handle arrow effects based on the bow type
        handleAbilities(world, pos);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        World world = this.getWorld();
        BlockPos pos = entityHitResult.getEntity().getBlockPos();
        //handle arrow effects based on the bow type
        handleAbilities(world, pos);
    }

    private void handleAbilities(World world, BlockPos pos) {
        // TNT
        if(type == AbilityBowType.TNT && !hasDoneAbility) {
            //surround the position with 4 tnt blocks set to explode, adjust the tnt blocks accordingly to be placed on empty block, if there is no empty block within 1 block of the selected position drop the placement of that tnt bloc
            for(int x = 0; x < 4; x++) {
                //spawn a primed tnt
                TntEntity entity = new TntEntity(world, pos.getX() + 0.5 + (double) x / 4.0, pos.getY() + 1, pos.getZ() + 0.5 + (double) x / 4.0, owner);
                //make tnt jump upward from spawn in a random direction
                Random rand = new Random();
                double dx = rand.nextDouble() * 0.2 - 0.1;
                double dy = rand.nextDouble() * 0.2 + 0.5;
                double dz = rand.nextDouble() * 0.2 - 0.1;
                entity.setVelocity(dx, dy, dz);
                //make tnt explode after 1.5 seconds
                entity.setFuse(30);
                //spawn the tnt entity
                world.spawnEntity(entity);
            }

        }
        // EXPLOSION
        else if(type == AbilityBowType.EXPLOSION &&!hasDoneAbility) {
            //explode at the arrow's position
            world.createExplosion(owner, pos.getX(), pos.getY(), pos.getZ(), 2.0F, World.ExplosionSourceType.TNT);
        }

        // WATER
        else if(type == AbilityBowType.WATER &&!hasDoneAbility) {
            if(world.getBlockState(pos.up()).isAir()) {
                world.setBlockState(pos.up(), Blocks.WATER.getDefaultState());
            }
            else {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
            }

        }

        hasDoneAbility = true; //set the arrow as used to prevent the ability from being used again
    }
}

