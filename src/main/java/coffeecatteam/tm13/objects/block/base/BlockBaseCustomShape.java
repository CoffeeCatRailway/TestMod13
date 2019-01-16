package coffeecatteam.tm13.objects.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

/**
 * @author CoffeeCatRailway
 * Created: 16/01/2019
 */
public abstract class BlockBaseCustomShape extends BlockBase {

    public BlockBaseCustomShape(String id, Material material) {
        super(id, material);
    }

    public abstract VoxelShape getShape();

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader reader, BlockPos pos) {
        return getShape();
    }
}
