package coffeecatteam.tm13.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.ShapeUtils;
import net.minecraft.util.math.shapes.VoxelShape;

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
    public VoxelShape getShape() {
        VoxelShape shape = ShapeUtils.or(SHAPE_BASE, ShapeUtils.or(SHAPE_POLE, ShapeUtils.or(SHAPE_POLE_SUPPORT_FRONT, ShapeUtils.or(SHAPE_POLE_SUPPORT_BACK, ShapeUtils.or(SHAPE_POLE_SUPPORT_LEFT, SHAPE_POLE_SUPPORT_RIGHT)))));
        return shape;
    }
}
