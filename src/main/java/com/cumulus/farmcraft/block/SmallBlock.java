package com.cumulus.farmcraft.block;

import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SmallBlock extends Block implements Waterloggable {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");

    double l = 4.0;
    double m = 12.0;

    VoxelShape middle  = Block.createCuboidShape(l, l, l, m, m, m);
    VoxelShape up = Block.createCuboidShape(l, m, l, m, 16.0, m);
    VoxelShape down = Block.createCuboidShape(l, 0.0, l, m, l, m);
    VoxelShape north = Block.createCuboidShape(l, l, 0.0, m, m, l);
    VoxelShape south = Block.createCuboidShape(l, l, m, m, m, 16.0);
    VoxelShape east = Block.createCuboidShape(m, l, l, 16.0, m, m);
    VoxelShape west = Block.createCuboidShape(0.0, l, l, l, m, m);

    public SmallBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(UP, false)).with(DOWN, false)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(Properties.WATERLOGGED, false));

    }

    private static VoxelShape method_24426(VoxelShape voxelShape, Boolean wallShape, VoxelShape voxelShape2, VoxelShape voxelShape3) {
        if (wallShape) {
            return VoxelShapes.union(voxelShape, voxelShape3);
        } else {
            return !wallShape ? VoxelShapes.union(voxelShape, voxelShape2) : voxelShape;
        }
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        for (Direction dir : Direction.values()) {
            if (world.getBlockState(pos.offset(dir)).getBlock() instanceof SmallBlock) {
                world.setBlockState(pos.offset(dir), getStateForNeighborUpdate(state, dir.getOpposite(), world.getBlockState(pos), world, pos.offset(dir), pos));
            }
        }
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {

        VoxelShape shape = middle;

        if (state.get(UP))
            shape = VoxelShapes.union(shape, up);

        if (state.get(DOWN))
            shape = VoxelShapes.union(shape, down);

        if (state.get(NORTH))
            shape = VoxelShapes.union(shape, north);

        if (state.get(EAST))
            shape = VoxelShapes.union(shape, east);

        if (state.get(SOUTH))
            shape = VoxelShapes.union(shape, south);

        if (state.get(WEST))
            shape = VoxelShapes.union(shape, west);


        return shape;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    private boolean shouldConnectTo(BlockState state, boolean faceFullSquare, Direction side) {
        return state.getBlock() instanceof SmallBlock || faceFullSquare;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos bpNorth = blockPos.north();
        BlockPos bpEast = blockPos.east();
        BlockPos bpSouth = blockPos.south();
        BlockPos bpWest = blockPos.west();
        BlockPos bpUp = blockPos.up();
        BlockPos bpDown = blockPos.down();
        BlockState bsNorth = worldView.getBlockState(bpNorth);
        BlockState bsEast = worldView.getBlockState(bpEast);
        BlockState bsSouth = worldView.getBlockState(bpSouth);
        BlockState bsWest = worldView.getBlockState(bpWest);
        BlockState bsUp = worldView.getBlockState(bpUp);
        BlockState bsDown = worldView.getBlockState(bpDown);
        boolean bl = this.shouldConnectTo(bsNorth, bsNorth.isSideSolidFullSquare(worldView, bpNorth, Direction.SOUTH), Direction.SOUTH);
        boolean bl2 = this.shouldConnectTo(bsEast, bsEast.isSideSolidFullSquare(worldView, bpEast, Direction.WEST), Direction.WEST);
        boolean bl3 = this.shouldConnectTo(bsSouth, bsSouth.isSideSolidFullSquare(worldView, bpSouth, Direction.NORTH), Direction.NORTH);
        boolean bl4 = this.shouldConnectTo(bsWest, bsWest.isSideSolidFullSquare(worldView, bpWest, Direction.EAST), Direction.EAST);
        boolean bl5 = this.shouldConnectTo(bsUp, bsUp.isSideSolidFullSquare(worldView, bpUp, Direction.DOWN), Direction.DOWN);
        boolean bl6 = this.shouldConnectTo(bsDown, bsDown.isSideSolidFullSquare(worldView, bpUp, Direction.UP), Direction.UP);
        BlockState blockState7 = (BlockState)this.getDefaultState().with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return this.method_24422(worldView, blockState7, bpDown, bsUp, bl, bl2, bl3, bl4, bl5, bl6);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if ((Boolean)state.get(Properties.WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        boolean up = world.getBlockState(pos.offset(Direction.UP)).getBlock() instanceof SmallBlock;
        boolean down = world.getBlockState(pos.offset(Direction.DOWN)).getBlock() instanceof SmallBlock || world.getBlockState(pos.offset(Direction.DOWN)).isSolidBlock(world, pos);
        boolean north = world.getBlockState(pos.offset(Direction.NORTH)).getBlock() instanceof SmallBlock;
        boolean south = world.getBlockState(pos.offset(Direction.SOUTH)).getBlock() instanceof SmallBlock;
        boolean east = world.getBlockState(pos.offset(Direction.EAST)).getBlock() instanceof SmallBlock;
        boolean west = world.getBlockState(pos.offset(Direction.WEST)).getBlock() instanceof SmallBlock;

        return state.with(UP, up).with(DOWN, down).with(NORTH, north).with(SOUTH, south).with(EAST, east).with(WEST, west);
    }

    private static boolean method_24424(BlockState blockState, BooleanProperty property) {
        return blockState.get(property) != false;
    }

    private static boolean method_24427(VoxelShape voxelShape, VoxelShape voxelShape2) {
        return !VoxelShapes.matchesAnywhere(voxelShape2, voxelShape, BooleanBiFunction.ONLY_FIRST);
    }


    private BlockState method_24422(WorldView worldView, BlockState blockState, BlockPos blockPos, BlockState blockState2, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        VoxelShape voxelShape = blockState2.getCollisionShape(worldView, blockPos);//.getFace(Direction.DOWN);
        BlockState blockState3 = this.method_24425(blockState, bl, bl2, bl3, bl4, bl5, bl6, voxelShape);
        return (BlockState)blockState3.with(DOWN, this.method_27092(blockState3, blockState2, voxelShape));
    }

    private boolean method_27092(BlockState blockState, BlockState blockState2, VoxelShape voxelShape) {
        boolean bl = blockState2.getBlock() instanceof SmallBlock && (Boolean)blockState2.get(UP);
        if (bl) {
            return true;
        } else {
            boolean bsNorth = blockState.get(NORTH);
            boolean bsSouth = blockState.get(SOUTH);
            boolean bsEast = blockState.get(EAST);
            boolean bsWest = blockState.get(WEST);
            boolean bl2 = !bsSouth;
            boolean bl3 = !bsWest;
            boolean bl4 = !bsEast;
            boolean bl5 = !bsNorth;
            boolean bl6 = bl5 && bl2 && bl3 && bl4 || bl5 != bl2 || bl3 != bl4;
            if (bl6) {
                return true;
            } else {
                boolean bl7 = true;
                if (bl7) {
                    return false;
                } else {
                    //return blockState2.getBlock().isIn(BlockTags.WALL_POST_OVERRIDE) || method_24427(voxelShape, TALL_POST_SHAPE);
                    return true;
                }
            }
        }
    }

    private BlockState method_24425(BlockState blockState, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, VoxelShape voxelShape) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, this.method_24428(bl, voxelShape, north))).with(DOWN, this.method_24428(bl6, voxelShape, down))).with(UP, this.method_24428(bl5, voxelShape, up))).with(EAST, this.method_24428(bl2, voxelShape, east))).with(SOUTH, this.method_24428(bl3, voxelShape, south))).with(WEST, this.method_24428(bl4, voxelShape, west));
    }

    private Boolean method_24428(boolean bl, VoxelShape voxelShape, VoxelShape voxelShape2) {
        if (bl) {
            return method_24427(voxelShape, voxelShape2) ? true : false;
        } else {
            return false;
        }
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !(Boolean)state.get(Properties.WATERLOGGED);
    }

    protected void appendProperties(net.minecraft.state.StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, Properties.WATERLOGGED);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(SOUTH))).with(EAST, state.get(WEST))).with(SOUTH, state.get(NORTH))).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(EAST))).with(EAST, state.get(SOUTH))).with(SOUTH, state.get(WEST))).with(WEST, state.get(NORTH));
            case CLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(NORTH, state.get(WEST))).with(EAST, state.get(NORTH))).with(SOUTH, state.get(EAST))).with(WEST, state.get(SOUTH));
            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return (BlockState)((BlockState)state.with(NORTH, state.get(SOUTH))).with(SOUTH, state.get(NORTH));
            case FRONT_BACK:
                return (BlockState)((BlockState)state.with(EAST, state.get(WEST))).with(WEST, state.get(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }
}