package coffeecatteam.tm13.init;

import coffeecatteam.tm13.objects.biome.BiomeMegaCanyons;
import coffeecatteam.tm13.objects.biome.BiomeTest;
import net.minecraft.world.biome.Biome;

/**
 * @author CoffeeCatRailway
 * Created: 16/01/2019
 */
public class InitBiome {

    public static final Biome TEST_BIOME = new BiomeTest("test_biome");
    public static final Biome MEGA_CANYONS = new BiomeMegaCanyons("mega_canyons");

    public static void init() {
        RegistrationHandler.registerBiome(TEST_BIOME);
        RegistrationHandler.registerBiome(MEGA_CANYONS);
    }
}
