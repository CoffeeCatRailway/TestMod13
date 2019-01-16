package coffeecatteam.tm13.objects.biome;

import coffeecatteam.tm13.TestMod13;
import net.minecraft.world.biome.Biome;

/**
 * @author CoffeeCatRailway
 * Created: 16/01/2019
 */
public class BiomeBase extends Biome {

    public BiomeBase(String id, BiomeBuilder biomeBuilder) {
        super(biomeBuilder);
        this.setRegistryName(TestMod13.MODID, id);
    }
}
