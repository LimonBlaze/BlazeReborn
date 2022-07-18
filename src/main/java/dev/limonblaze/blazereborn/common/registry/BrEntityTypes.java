package dev.limonblaze.blazereborn.common.registry;

import dev.limonblaze.blazereborn.api.BlazeRebornAPI;
import dev.limonblaze.blazereborn.common.entity.monster.SoulBlaze;
import dev.limonblaze.blazereborn.common.entity.monster.SoulMagmaCube;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, BlazeRebornAPI.MODID);

    public static final RegistryObject<EntityType<SoulBlaze>> SOUL_BLAZE = register("soul_blaze",
        EntityType.Builder.of(dev.limonblaze.blazereborn.common.entity.monster.SoulBlaze::new, MobCategory.MONSTER)
            .fireImmune()
            .sized(0.6F, 1.8F)
            .clientTrackingRange(8)
    );

    public static final RegistryObject<EntityType<SoulMagmaCube>> SOUL_MAGMA_CUBE = register("soul_magma_cube",
        EntityType.Builder.of(SoulMagmaCube::new, MobCategory.MONSTER)
            .fireImmune()
            .sized(2.04F, 2.04F)
            .clientTrackingRange(8)
    );

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPE.register(name, () -> builder.build(new ResourceLocation(BlazeRebornAPI.MODID, name).toString()));
    }

}
