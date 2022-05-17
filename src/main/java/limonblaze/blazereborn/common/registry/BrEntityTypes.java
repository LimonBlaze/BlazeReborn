package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.entity.ProtectedItemEntity;
import limonblaze.blazereborn.common.entity.monster.SoulBlaze;
import limonblaze.blazereborn.common.entity.monster.SoulMagmaCube;
import limonblaze.blazereborn.common.entity.projectile.IntegratedFishingHook;
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
        EntityType.Builder.of(limonblaze.blazereborn.common.entity.monster.SoulBlaze::new, MobCategory.MONSTER)
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

    public static final RegistryObject<EntityType<ProtectedItemEntity>> PROTECTED_ITEM = register("protected_item",
        EntityType.Builder.<ProtectedItemEntity>of(ProtectedItemEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .clientTrackingRange(6)
            .updateInterval(20)
    );

    public static final RegistryObject<EntityType<IntegratedFishingHook.Blaze>> BLAZE_FISHING_HOOK = register("blaze_fishing_bobber",
        EntityType.Builder.<IntegratedFishingHook.Blaze>of(IntegratedFishingHook.Blaze::new, MobCategory.MISC)
            .noSave()
            .noSummon()
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(5)
    );

    public static final RegistryObject<EntityType<IntegratedFishingHook.SoulBlaze>> SOUL_BLAZE_FISHING_HOOK = register("soul_blaze_fishing_bobber",
        EntityType.Builder.<IntegratedFishingHook.SoulBlaze>of(IntegratedFishingHook.SoulBlaze::new, MobCategory.MISC)
            .noSave()
            .noSummon()
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(5)
    );

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPE.register(name, () -> builder.build(new ResourceLocation(BlazeRebornAPI.MODID, name).toString()));
    }

}
