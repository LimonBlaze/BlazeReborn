package limonblaze.blazereborn.common.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeTags {

    public static class Blocks {

        public static TagKey<Block> BREWING_STANDS = create("brewing_stands");

        public static TagKey<Block> create(String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", name));
        }

    }

    public static class Items {

        public static TagKey<Item> BREWING_STANDS = create("brewing_stands");
        public static TagKey<Item> FIRE_CHARGES = create("fire_charges");
        public static TagKey<Item> FISHING_RODS = create("fishing_rods");
        public static TagKey<Item> RODS = create("rods");
        public static TagKey<Item> POWDERS = create("powders");
        public static TagKey<Item> RODS$BLAZE = create("rods/blaze");
        public static TagKey<Item> POWDERS$BLAZE = create("powders/blaze");

        public static TagKey<Item> create(String name) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", name));
        }

    }

    public static class EntityTypes {

        public static TagKey<EntityType<?>> ITEMS = create("items");
        public static TagKey<EntityType<?>> BLAZES = create("blazes");
        public static TagKey<EntityType<?>> MAGMA_CUBES = create("magma_cubes");

        public static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge", name));
        }

    }

}
