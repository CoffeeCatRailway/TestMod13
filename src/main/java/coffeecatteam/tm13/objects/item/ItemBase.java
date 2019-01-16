package coffeecatteam.tm13.objects.item;

import coffeecatteam.tm13.TestMod13;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatRailway
 * Created: 14/01/2019
 */
public class ItemBase extends Item {

    public ItemBase(String id, ItemGroup group) {
        this(id, new Builder().group(group));
    }

    public ItemBase(String id, Builder builder) {
        super(builder);
        this.setRegistryName(new ResourceLocation(TestMod13.MODID, id));
    }
}
