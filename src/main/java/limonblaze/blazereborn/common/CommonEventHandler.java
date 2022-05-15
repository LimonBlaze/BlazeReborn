package limonblaze.blazereborn.common;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.api.event.BlazeSpawnerModificationEvent;
import limonblaze.blazereborn.api.event.EntityFireVariantEvent;
import limonblaze.blazereborn.api.event.EntityOnFireIntervalEvent;
import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import limonblaze.blazereborn.common.crafting.recipe.BrewingMixRecipe;
import limonblaze.blazereborn.common.data.tag.MultiBlazesBiomeTags;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.mixin.access.BrewingRecipeRegistryAccessor;
import limonblaze.blazereborn.util.BlazeRebornConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.stream.Collectors;

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

    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void onDatapackReload(OnDatapackSyncEvent event) {
        if(event.getPlayer() == null) {
            BrewingRecipeRegistryAccessor.accessRecipes().removeAll(BlazeReborn.BREWING_MIX_RECIPES_CACHE);
            RecipeManager manager = event.getPlayerList().getServer().getRecipeManager();
            BlazeReborn.BREWING_MIX_RECIPES_CACHE.clear();
            manager.getRecipes().forEach(recipe -> {
                if(recipe instanceof BrewingMixRecipe<?> brewingMixRecipe) {
                    BlazeReborn.BREWING_MIX_RECIPES_CACHE.add(brewingMixRecipe);
                }
            });
            BrewingRecipeRegistryAccessor.accessRecipes().addAll(BlazeReborn.BREWING_MIX_RECIPES_CACHE);
        }
    }

}