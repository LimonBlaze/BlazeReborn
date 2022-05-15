package limonblaze.blazereborn.common;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.api.event.BlazeSpawnerModificationEvent;
import limonblaze.blazereborn.api.event.EntityFireVariantEvent;
import limonblaze.blazereborn.api.event.EntityOnFireIntervalEvent;
import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import limonblaze.blazereborn.common.crafting.recipe.BrewingPotionRecipe;
import limonblaze.blazereborn.common.data.tag.BlazeRebornBiomeTags;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.mixin.access.BrewingRecipeRegistryAccessor;
import limonblaze.blazereborn.util.BlazeRebornConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.OnDatapackSyncEvent;
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
           event.getWorld().getBiome(blaze.blockPosition()).is(BlazeRebornBiomeTags.SPAWNS_SOUL_VARIANT_MOBS)
        ) {
            blaze.convertTo(BlazeRebornEntityTypes.SOUL_BLAZE.get(), true);
        }
    }

    @SubscribeEvent
    public static void onBlazeSpawnerGen(BlazeSpawnerModificationEvent event) {
        if(BlazeRebornConfig.SERVER.entityGeneration.convertBlazeSpawners.get() &&
           event.getLevel().getBiome(event.getPos()).is(BlazeRebornBiomeTags.SPAWNS_SOUL_VARIANT_MOBS)) {
           event.setType(BlazeRebornEntityTypes.SOUL_BLAZE.get());
           event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void onDatapackReload(OnDatapackSyncEvent event) {
        if(event.getPlayer() == null) {
            BrewingRecipeRegistryAccessor.accessRecipes().removeAll(BlazeReborn.BREWING_POTION_RECIPES);
            RecipeManager manager = event.getPlayerList().getServer().getRecipeManager();
            BlazeReborn.BREWING_POTION_RECIPES.clear();
            manager.getRecipes().forEach(recipe -> {
                if(recipe instanceof BrewingPotionRecipe brewingPotionRecipe) {
                    BlazeReborn.BREWING_POTION_RECIPES.add(brewingPotionRecipe);
                }
            });
            BrewingRecipeRegistryAccessor.accessRecipes().addAll(BlazeReborn.BREWING_POTION_RECIPES);
        }
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ResourceLocation biomeId = event.getName();
        if(Biomes.SOUL_SAND_VALLEY.location().equals(biomeId) && BlazeRebornConfig.SERVER.entityGeneration.generateSoulMagmaCubes.get()) {
            event.getSpawns()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(
                    BlazeRebornEntityTypes.SOUL_MAGMA_CUBE.get(),
                    20, 4, 4
                    )
                )
                .addMobCharge(BlazeRebornEntityTypes.SOUL_MAGMA_CUBE.get(), 0.7D, 0.15D);
        }
    }

}