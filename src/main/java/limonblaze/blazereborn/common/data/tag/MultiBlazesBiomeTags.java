package limonblaze.blazereborn.common.data.tag;

import limonblaze.blazereborn.BlazeReborn;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MultiBlazesBiomeTags {

    public static final TagKey<Biome> SOUL_BLAZE_SPAWNING_BIOMES = create(BlazeReborn.id("soul_blaze_spawning_biomes"));

    public static TagKey<Biome> create(ResourceLocation id) {
        return TagKey.create(Registry.BIOME_REGISTRY, id);
    }

}
