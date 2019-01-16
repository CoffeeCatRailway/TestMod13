package coffeecatteam.tm13.objects.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ShapeUtils;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 15/01/2019
 */
public class BlockBaseSlab extends BlockBase implements IBucketPickupHandler, ILiquidContainer {

    public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");

    protected static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BlockBaseSlab(String id, Material material) {
        super(id, material);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false).with(LAVALOGGED, false));
    }

    @Override
    public int getOpacity(IBlockState state, IBlockReader reader, BlockPos pos) {
        return reader.getMaxLightLevel();
    }

    @Override
    protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(TYPE, WATERLOGGED, LAVALOGGED);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader reader, BlockPos pos) {
        SlabType slabtype = state.get(TYPE);
        switch (slabtype) {
            case DOUBLE:
                return ShapeUtils.fullCube();
            case TOP:
                return TOP_SHAPE;
            default:
                return BOTTOM_SHAPE;
        }
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return state.get(TYPE) == SlabType.DOUBLE || state.get(TYPE) == SlabType.TOP;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader reader, IBlockState state, BlockPos pos, EnumFacing face) {
        SlabType slabtype = state.get(TYPE);
        if (slabtype == SlabType.DOUBLE) {
            return BlockFaceShape.SOLID;
        } else if (face == EnumFacing.UP && slabtype == SlabType.TOP) {
            return BlockFaceShape.SOLID;
        } else {
            return face == EnumFacing.DOWN && slabtype == SlabType.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
    }

    @Override
    @Nullable
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockState blockState = context.getWorld().getBlockState(context.getPos());
        if (blockState.getBlock() == this) {
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false).with(LAVALOGGED, false);
        } else {
            IFluidState fluidState = context.getWorld().getFluidState(context.getPos());
            IBlockState blockState1 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(LAVALOGGED, fluidState.getFluid() == Fluids.LAVA);
            EnumFacing enumfacing = context.getFace();
            return enumfacing != EnumFacing.DOWN && (enumfacing == EnumFacing.UP || !((double) context.getHitY() > 0.5D)) ? blockState1 : blockState1.with(TYPE, SlabType.TOP);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, Random random) {
        return state.get(TYPE) == SlabType.DOUBLE ? 2 : 1;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return state.get(TYPE) == SlabType.DOUBLE;
    }

    @Override
    public boolean isReplaceable(IBlockState state, BlockItemUseContext context) {
        ItemStack itemstack = context.getItem();
        SlabType slabtype = state.get(TYPE);
        if (slabtype != SlabType.DOUBLE && itemstack.getItem() == this.asItem()) {
            if (context.replacingClickedOnBlock()) {
                boolean flag = (double) context.getHitY() > 0.5D;
                EnumFacing enumfacing = context.getFace();
                if (slabtype == SlabType.BOTTOM) {
                    return enumfacing == EnumFacing.UP || flag && enumfacing.getAxis().isHorizontal();
                } else {
                    return enumfacing == EnumFacing.DOWN || !flag && enumfacing.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public Fluid pickupFluid(IWorld world, BlockPos pos, IBlockState state) {
        if (state.get(WATERLOGGED) && !state.get(LAVALOGGED)) {
            world.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        }
        if (state.get(LAVALOGGED) && !state.get(WATERLOGGED)) {
            world.setBlockState(pos, state.with(LAVALOGGED, false), 3);
            return Fluids.LAVA;
        }
        return Fluids.EMPTY;
    }

    @Override
    public IFluidState getFluidState(IBlockState state) {
        if (state.get(WATERLOGGED) && !state.get(LAVALOGGED))
            return Fluids.WATER.getStillFluidState(false);
        if (state.get(LAVALOGGED) && !state.get(WATERLOGGED))
            return Fluids.LAVA.getStillFluidState(false);
        return super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader blockReader, BlockPos pos, IBlockState state, Fluid fluid) {
        return state.get(TYPE) != SlabType.DOUBLE && ((!state.get(WATERLOGGED) && !state.get(LAVALOGGED) && fluid == Fluids.WATER) || (!state.get(LAVALOGGED) && !state.get(WATERLOGGED) && fluid == Fluids.LAVA));
    }

    @Override
    public boolean receiveFluid(IWorld world, BlockPos pos, IBlockState state, IFluidState fluidState) {
        if (state.get(TYPE) == SlabType.DOUBLE)
            return false;

        if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!world.isRemote()) {
                world.setBlockState(pos, state.with(WATERLOGGED, true), 3);
                world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        }
        if (!state.get(LAVALOGGED) && fluidState.getFluid() == Fluids.LAVA) {
            if (!world.isRemote()) {
                world.setBlockState(pos, state.with(LAVALOGGED, true), 3);
                world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        }

        return false;
    }

    @Override
    public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED) && !state.get(LAVALOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (state.get(LAVALOGGED) && !state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.LAVA, Fluids.LAVA.getTickRate(world));
        }

        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean allowsMovement(IBlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        switch (type) {
            case LAND:
                return state.get(TYPE) == SlabType.BOTTOM;
            case WATER:
                return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }
}
