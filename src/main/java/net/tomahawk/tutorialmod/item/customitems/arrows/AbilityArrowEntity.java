package net.tomahawk.tutorialmod.item.customitems.arrows;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tomahawk.tutorialmod.item.customitems.enums.AbilityBowType;

public class AbilityArrowEntity extends PersistentProjectileEntity {

    public final AbilityBowType type;

    public AbilityArrowEntity(World world, LivingEntity owner, AbilityBowType type) {
        super(EntityType.ARROW, owner, world);
        this.type = type;
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
        if(type == AbilityBowType.TNT) {
            BlockPos pos = blockHitResult.getBlockPos();

        }

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

