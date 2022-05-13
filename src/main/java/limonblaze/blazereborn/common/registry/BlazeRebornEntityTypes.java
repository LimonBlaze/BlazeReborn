package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.common.entity.monster.SoulBlaze;
import limonblaze.blazereborn.common.entity.projectile.VariantedSmallFireball;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlazeRebornEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, BlazeReborn.MODID);

    public static final RegistryObject<EntityType<VariantedSmallFireball>> VARIANTED_SMALL_FIREBALL = register("varianted_small_fireball",
        EntityType.Builder.<VariantedSmallFireball>of(VariantedSmallFireball::new, MobCategory.MISC)
            .sized(0.3125F, 0.3125F)
            .clientTrackingRange(4)
            .updateInterval(10)
    );

    public static final RegistryObject<EntityType<SoulBlaze>> SOUL_BLAZE = register("soul_blaze",
        EntityType.Builder.of(SoulBlaze::new, MobCategory.MONSTER)
            .fireImmune()
            .sized(0.6F, 1.8F)
            .clientTrackingRange(8)
    );

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPE.register(name, () -> builder.build(new ResourceLocation(BlazeReborn.MODID, name).toString()));
    }

}
