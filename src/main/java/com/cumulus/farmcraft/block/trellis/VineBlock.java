package com.cumulus.farmcraft.block.trellis;

import com.cumulus.farmcraft.block.Blocks;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Random;

public class VineBlock extends TrellisBlock implements Tickable {

    public Identifier id;

    public static final IntProperty AGE = Properties.AGE_3;

    public VineBlock(Settings settings, Identifier id) {
        super(settings);
        this.id = id;
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
        if (random.nextInt(2) == 0) {
            Direction dir = getRandomDirection(random.nextInt(6));
            boolean free = world.getBlockState(pos.offset(dir)).getBlock() instanceof TrellisBlock && !(world.getBlockState(pos.offset(dir)).getBlock() instanceof VineBlock);

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

    public void register(Identifier id) {
        Registry.register(Registry.BLOCK, id, this);
        //this.entity = Registry.register(Registry.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.create( () -> new VineBlockEntity(id), this).build(null));
    }
}
