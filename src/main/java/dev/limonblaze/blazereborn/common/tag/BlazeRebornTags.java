package dev.limonblaze.blazereborn.common.tag;

import dev.limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class BlazeRebornTags {

    public static class Biomes {

        public static final TagKey<Biome> SPAWNS_SOUL_VARIANT_MOBS = create("spawns_soul_variant_mobs");

        public static TagKey<Biome> create(String name) {
            return TagKey.create(Registry.BIOME_REGISTRY, BlazeRebornAPI.id(name));
        }

    }

}
