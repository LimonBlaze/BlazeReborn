package limonblaze.blazereborn.common.data.tag;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MultiBlazesBiomeTags {

    public static final TagKey<Biome> SOUL_BLAZE_SPAWNING_BIOMES = create(BlazeRebornAPI.id("spawns_soul_variant_mobs"));

    public static TagKey<Biome> create(ResourceLocation id) {
        return TagKey.create(Registry.BIOME_REGISTRY, id);
    }

}
