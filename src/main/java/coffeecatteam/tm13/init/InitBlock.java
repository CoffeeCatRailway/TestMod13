package coffeecatteam.tm13.init;

import coffeecatteam.tm13.TestMod13;
import coffeecatteam.tm13.objects.block.BlockTest;
import coffeecatteam.tm13.objects.block.base.BlockBaseSlab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author CoffeeCatRailway
 * Created: 14/01/2019
 */
public class InitBlock {

    public static final Block TEST_BLOCK = new BlockTest("test_block", Material.ROCK);
    public static final Block TEST_BLOCK_SLAB = new BlockBaseSlab("test_block_slab", Material.ROCK);

    public static void init() {
        RegistrationHandler.registerBlock(TestMod13.GroupTM13, TEST_BLOCK, TEST_BLOCK_SLAB);
    }
}
