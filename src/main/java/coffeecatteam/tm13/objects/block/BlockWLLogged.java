package coffeecatteam.tm13.objects.block;

import coffeecatteam.tm13.objects.block.base.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 15/01/2019
 */
public abstract class BlockWLLogged extends BlockBase implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");

    public BlockWLLogged(String id, Material material) {
        super(id, material);
    }

    @Override
    public int getOpacity(IBlockState state, IBlockReader reader, BlockPos pos) {
        return reader.getMaxLightLevel();
    }

    @Override
    protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(WATERLOGGED, LAVALOGGED);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nullable
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockState blockState = context.getWorld().getBlockState(context.getPos());
        if (blockState.getBlock() == this) {
            return blockState.with(WATERLOGGED, false).with(LAVALOGGED, false);
        } else {
            IFluidState fluidState = context.getWorld().getFluidState(context.getPos());
            IBlockState blockState1 = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(LAVALOGGED, fluidState.getFluid() == Fluids.LAVA);
            return blockState1;
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
        return (!state.get(WATERLOGGED) && !state.get(LAVALOGGED) && fluid == Fluids.WATER) || (!state.get(LAVALOGGED) && !state.get(WATERLOGGED) && fluid == Fluids.LAVA);
    }

    @Override
    public boolean receiveFluid(IWorld world, BlockPos pos, IBlockState state, IFluidState fluidState) {
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
}
