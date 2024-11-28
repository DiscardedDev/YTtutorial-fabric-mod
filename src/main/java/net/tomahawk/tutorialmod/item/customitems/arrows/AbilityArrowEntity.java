package net.tomahawk.tutorialmod.item.customitems.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
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

    public void initFromStack(ItemStack stack) {

    }

    @Override
    public void tick() {
        super.tick();

//        if (this.getWorld().isClient) {
//            if (this.inGround) {
//                if (this.inGroundTime % 5 == 0) {
//                    this.spawnParticles(1);
//                }
//            } else {
//                this.spawnParticles(2);
//            }
//        } else
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
            //spawn water in area around the position if block is air
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        BlockPos newPos = pos.add(x, y, z);
                        if(world.getBlockState(newPos).isAir()) {
                            world.setBlockState(newPos, Blocks.WATER.getDefaultState());
                        }
                    }
                }
            }

        }

        hasDoneAbility = true; //set the arrow as used to prevent the ability from being used again
    }

}
//    private void spawnParticles(int amount) {
//        int i = this.getColor();
//        if (i != -1 && amount > 0) {
//            double d = (double)(i >> 16 & 0xFF) / 255.0;
//            double e = (double)(i >> 8 & 0xFF) / 255.0;
//            double f = (double)(i >> 0 & 0xFF) / 255.0;
//
//            for (int j = 0; j < amount; j++) {
//                this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
//            }
//        }

//    public int getColor() {
//        return this.dataTracker.get(COLOR);
//    }
//
//    private void setColor(int color) {
//        this.colorSet = true;
//        this.dataTracker.set(COLOR, color);
//    }

