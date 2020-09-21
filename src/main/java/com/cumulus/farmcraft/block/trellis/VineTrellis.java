package com.cumulus.farmcraft.block.trellis;

import com.cumulus.farmcraft.block.Blocks;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class VineTrellis extends TrellisBlock implements Tickable {


    public VineTrellis(Settings settings) {
        super(settings.strength(0.5f));



    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        //world.syncWorldEvent(player, 2001, pos, getRawIdFromState(state));

        world.setBlockState(pos, Blocks.TRELLIS_BLOCK.getDefaultState());
        player.playSound(SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, 1.0f, 1.0f);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        dropStacks(state, world, pos, blockEntity, player, stack);
        //world.setBlockState(pos, Blocks.TRELLIS_BLOCK.getDefaultState());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 5) {
            spreadToNeighbors(state, world, pos, random);
        }

    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void spreadToNeighbors(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            Direction dir = getRandomDirection(random.nextInt(6));
            boolean free = world.getBlockState(pos.offset(dir)).getBlock() instanceof TrellisBlock && !(world.getBlockState(pos.offset(dir)).getBlock() instanceof VineTrellis);

            if (free) {
                world.setBlockState(pos.offset(dir), state.getStateForNeighborUpdate(dir.getOpposite(), state, world, pos.offset(dir), pos));
            }
        }
    }

    public Direction getRandomDirection(int value) {
        if (value == 0)
            return Direction.UP;
        if (value == 1)
            return Direction.DOWN;
        if (value == 2)
            return Direction.NORTH;
        if (value == 3)
            return Direction.EAST;
        if (value == 4)
            return Direction.SOUTH;
        else
            return Direction.WEST;
    }

    @Override
    public void tick() {

    }
}
