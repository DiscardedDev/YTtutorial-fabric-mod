package net.tomahawk.tutorialmod.item.customitems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.tomahawk.tutorialmod.util.ModTags;

public class MetalDetectorItem extends Item {

    public MetalDetectorItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient()) {
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;
            if (player != null) {
                for (int i = 0; i <= (context.getBlockPos().getY() + 64); i++) {
                    BlockState currentBlockState = context.getWorld().getBlockState(context.getBlockPos().down(i)); //get clicked block position and move down by i
                    if (currentBlockState.isIn(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS)) {
                        AlertPlayer(player, context.getBlockPos().down(i), currentBlockState.getBlock().asItem());
                        foundBlock = true;
                        break;
                    }
                }
                if (!foundBlock) {
                    player.sendMessage(Text.literal("No Valuable Found!"));
                }
                context.getStack().damage(1, context.getPlayer(), playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand())); //deceasing the durability of the item in the players hand, ie the metal detector
            }
        }
        return ActionResult.SUCCESS;
    }

    public void AlertPlayer(PlayerEntity player, BlockPos pos, Item result){
        player.sendMessage(Text.literal("Found " + result.getName().getString() + " at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")"));
    }

}
