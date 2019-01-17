package coffeecatteam.tm13.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ShapeUtils;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @author CoffeeCatRailway
 * Created: 15/01/2019
 */
public class BlockTest extends BlockWLLogged {

    private static final VoxelShape SHAPE_BASE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
    private static final VoxelShape SHAPE_POLE = Block.makeCuboidShape(6.0D, 1.5D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape SHAPE_POLE_SUPPORT_FRONT = Block.makeCuboidShape(7.0D, 1.5D, 4.0D, 9.0D, 6.0D, 6.0D);
    private static final VoxelShape SHAPE_POLE_SUPPORT_BACK = Block.makeCuboidShape(7.0D, 1.5D, 10.0D, 9.0D, 6.0D, 12.0D);
    private static final VoxelShape SHAPE_POLE_SUPPORT_LEFT = Block.makeCuboidShape(4.0D, 1.5D, 7.0D, 6.0D, 6.0D, 9.0D);
    private static final VoxelShape SHAPE_POLE_SUPPORT_RIGHT = Block.makeCuboidShape(10.0D, 1.5D, 7.0D, 12.0D, 6.0D, 9.0D);

    public BlockTest(String id, Material material) {
        super(id, material);
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader reader, BlockPos pos) {
        VoxelShape shape = ShapeUtils.or(SHAPE_BASE, ShapeUtils.or(SHAPE_POLE, ShapeUtils.or(SHAPE_POLE_SUPPORT_FRONT, ShapeUtils.or(SHAPE_POLE_SUPPORT_BACK, ShapeUtils.or(SHAPE_POLE_SUPPORT_LEFT, SHAPE_POLE_SUPPORT_RIGHT)))));
        return shape;
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return player.addItemStackToInventory(new ItemStack(Items.DIAMOND, world.getRandom().nextInt(64) + 1));
    }
}
