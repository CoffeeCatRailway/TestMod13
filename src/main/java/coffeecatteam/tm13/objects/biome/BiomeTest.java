package coffeecatteam.tm13.objects.biome;

import coffeecatteam.tm13.init.InitBlock;
import coffeecatteam.tm13.objects.block.base.BlockBaseSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.state.properties.SlabType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

/**
 * @author CoffeeCatRailway
 * Created: 16/01/2019
 */
public class BiomeTest extends BiomeBase {

    public static final IBlockState WATER_TEST_SLAB = InitBlock.TEST_BLOCK_SLAB.getDefaultState().with(BlockBaseSlab.TYPE, SlabType.BOTTOM).with(BlockBaseSlab.WATERLOGGED, true).with(BlockBaseSlab.LAVALOGGED, false);
    public static final IBlockState DOUBLE_TEST_SLAB = InitBlock.TEST_BLOCK_SLAB.getDefaultState().with(BlockBaseSlab.TYPE, SlabType.DOUBLE).with(BlockBaseSlab.WATERLOGGED, false).with(BlockBaseSlab.LAVALOGGED, false);
    public static final SurfaceBuilderConfig TEST_SURFACE = new SurfaceBuilderConfig(WATER_TEST_SLAB, DOUBLE_TEST_SLAB, SAND);

    public BiomeTest(String id) {
        super(id, new BiomeBuilder().surfaceBuilder(new CompositeSurfaceBuilder<>(DEFAULT_SURFACE_BUILDER, TEST_SURFACE)).precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.5F).scale(2.0F).temperature(0.1F).downfall(0.1F).waterColor(4159204).waterFogColor(329011).parent(null));

        this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_WORLD_CARVER, new ProbabilityConfig(2.0F)));
        this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CANYON_WORLD_CARVER, new ProbabilityConfig(2.0F)));

        this.addStructure(Feature.MINESHAFT, new MineshaftConfig(1.0D, MineshaftStructure.Type.MESA));

        this.addStructureFeatures();
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, DOUBLE_TEST_SLAB, 20), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 255)));

        this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 100, 10, 11));
        this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 100, 10, 11));
        this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 100, 10, 11));
        this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 100, 10, 11));
        this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.HORSE, 100, 10, 11));
        this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.DONKEY, 100, 10, 11));
        this.addSpawn(EnumCreatureType.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 100, 10, 11));
        this.addSpawn(EnumCreatureType.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 100, 10, 11));
        this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 100, 10, 11));
    }
}
