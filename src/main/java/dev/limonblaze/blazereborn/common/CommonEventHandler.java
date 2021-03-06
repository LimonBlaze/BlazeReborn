package dev.limonblaze.blazereborn.common;

import dev.limonblaze.blazereborn.api.event.BlazeSpawnerModificationEvent;
import dev.limonblaze.blazereborn.api.event.EntityFireVariantEvent;
import dev.limonblaze.blazereborn.api.event.EntityOnFireIntervalEvent;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import dev.limonblaze.blazereborn.common.registry.BrEntityTypes;
import dev.limonblaze.blazereborn.common.tag.BlazeRebornTags;
import dev.limonblaze.blazereborn.util.BlazeRebornConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
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
           event.getWorld().getBiome(blaze.blockPosition()).is(BlazeRebornTags.Biomes.SPAWNS_SOUL_VARIANT_MOBS)
        ) {
            blaze.convertTo(BrEntityTypes.SOUL_BLAZE.get(), true);
        }
    }

    @SubscribeEvent
    public static void onBlazeSpawnerGen(BlazeSpawnerModificationEvent event) {
        if(BlazeRebornConfig.SERVER.entityGeneration.convertBlazeSpawners.get() &&
           event.getLevel().getBiome(event.getPos()).is(BlazeRebornTags.Biomes.SPAWNS_SOUL_VARIANT_MOBS)) {
           event.setType(BrEntityTypes.SOUL_BLAZE.get());
           event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ResourceLocation biomeId = event.getName();
        if(Biomes.SOUL_SAND_VALLEY.location().equals(biomeId) && BlazeRebornConfig.SERVER.entityGeneration.generateSoulMagmaCubes.get()) {
            event.getSpawns()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(
                    BrEntityTypes.SOUL_MAGMA_CUBE.get(),
                    20, 4, 4
                    )
                )
                .addMobCharge(BrEntityTypes.SOUL_MAGMA_CUBE.get(), 0.7D, 0.15D);
        }
    }

}