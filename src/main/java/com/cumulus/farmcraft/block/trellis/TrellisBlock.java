package com.cumulus.farmcraft.block.trellis;

import com.cumulus.farmcraft.Farmcraft;
import com.cumulus.farmcraft.VinePlants;
import com.cumulus.farmcraft.block.Blocks;
import com.cumulus.farmcraft.block.SmallBlock;
import com.cumulus.farmcraft.item.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class TrellisBlock extends SmallBlock implements BlockEntityProvider {

    private BlockEntityType<TrellisBlockEntity> entity;
    private VineBlock growing;

    public TrellisBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack item = player.getStackInHand(hand);

        if (item.getItem() == VinePlants.grapes.getItem()) {

            world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            this.growing = VinePlants.grapes.getBlock();

            if(!world.isClient) {
                BlockState newBlock = this.growing.getCurrentState(state);//  growing.getDefaultState().with(GrapeTrellis.NORTH, this.NORTH.getValues().iterator().next());

                world.setBlockState(pos, newBlock);
            }
            item.decrement(1);
            return ActionResult.PASS;
        }


        return ActionResult.FAIL;
    }

    protected BlockState getCurrentState(BlockState state) {
        return this.getDefaultState().with(UP, state.get(UP)).with(DOWN, state.get(DOWN)).with(NORTH, state.get(NORTH)).with(EAST, state.get(EAST)).with(SOUTH, state.get(SOUTH))
                .with(WEST, state.get(WEST));
    }


    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TrellisBlockEntity(Farmcraft.id("trellis_block"));
    }
}
