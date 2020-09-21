package com.cumulus.farmcraft.block.trellis;

import com.cumulus.farmcraft.Farmcraft;
import com.cumulus.farmcraft.block.SmallBlock;
import com.cumulus.farmcraft.item.Items;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TrellisBlock extends SmallBlock implements BlockEntityProvider {

    private BlockEntityType<TrellisBlockEntity> entity;

    public TrellisBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack item = player.getStackInHand(hand);

        if (item.getItem() == Items.GRAPES) {

            return ActionResult.PASS;
        }


        return ActionResult.FAIL;
    }


    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TrellisBlockEntity(Farmcraft.id("trellis_block"));
    }
}
