package limonblaze.blazereborn.common;

import limonblaze.blazereborn.api.event.BlazeSpawnerModificationEvent;
import limonblaze.blazereborn.api.event.EntityFireVariantEvent;
import limonblaze.blazereborn.api.event.EntityOnFireIntervalEvent;
import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import limonblaze.blazereborn.common.data.tag.MultiBlazesBiomeTags;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.util.BlazeRebornConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEventHandler {

    @SubscribeEvent
    public static void onEntityOnFireInterval(EntityOnFireIntervalEvent event) {
        event.setInterval(((FireVariantHoldingEntity) event.getEntity()).getFireVariant().damageInterval(event.getInterval()));
    }

    @SubscribeEvent
    public static void onEntityFireVariantAttack(EntityFireVariantEvent.Attack event) {
        event.setAmount(event.getFireVariant().onFireDamage(event.getEntity(), event.getSource(), event.getAmount()));
    }

    @SubscribeEvent
    public static void onBlazeSpawn(LivingSpawnEvent event) {
        if(BlazeRebornConfig.SERVER.entityGeneration.convertNaturallySpawnedBlazes.get() &&
           event.getEntityLiving() instanceof Blaze blaze &&
           blaze.getType() == EntityType.BLAZE &&
           event.getWorld().getBiome(blaze.blockPosition()).is(MultiBlazesBiomeTags.SOUL_BLAZE_SPAWNING_BIOMES)
        ) {
            blaze.convertTo(BlazeRebornEntityTypes.SOUL_BLAZE.get(), true);
        }
    }

    @SubscribeEvent
    public static void onBlazeSpawnerGen(BlazeSpawnerModificationEvent event) {
        if(BlazeRebornConfig.SERVER.entityGeneration.convertBlazeSpawners.get() &&
           event.getLevel().getBiome(event.getPos()).is(MultiBlazesBiomeTags.SOUL_BLAZE_SPAWNING_BIOMES)) {
           event.setType(BlazeRebornEntityTypes.SOUL_BLAZE.get());
           event.setCanceled(true);
        }
    }

}
