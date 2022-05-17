package limonblaze.blazereborn.common.tag;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

public class BlazeRebornTags {

    public static class EntityTypes {

        public static TagKey<EntityType<?>> SOUL_VARIANT_MOBS = create("soul_variant_mobs");

        public static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, BlazeRebornAPI.id(name));
        }

    }

    public static class Biomes {

        public static final TagKey<Biome> SPAWNS_SOUL_VARIANT_MOBS = create("spawns_soul_variant_mobs");

        public static TagKey<Biome> create(String name) {
            return TagKey.create(Registry.BIOME_REGISTRY, BlazeRebornAPI.id(name));
        }

    }

}
