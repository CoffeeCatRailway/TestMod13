package coffeecatteam.tm13.objects.block.base;

import coffeecatteam.tm13.TestMod13;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 14/01/2019
 */
public class BlockBase extends Block {

    public BlockBase(String id, Material material) {
        this(id, Builder.create(material));
    }

    public BlockBase(String id, Builder builder) {
        super(builder);
        this.setRegistryName(new ResourceLocation(TestMod13.MODID, id));
    }
}
