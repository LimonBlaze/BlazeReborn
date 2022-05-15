package limonblaze.blazereborn.client;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.common.crafting.recipe.BrewingMixRecipe;
import limonblaze.blazereborn.mixin.access.BrewingRecipeRegistryAccessor;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onUpdateRecipeManager(RecipesUpdatedEvent event) {
        BrewingRecipeRegistryAccessor.accessRecipes().removeAll(BlazeReborn.BREWING_MIX_RECIPES_CACHE);
        RecipeManager manager = event.getRecipeManager();
        BlazeReborn.BREWING_MIX_RECIPES_CACHE.clear();
        manager.getRecipes().forEach(recipe -> {
            if(recipe instanceof BrewingMixRecipe<?> brewingMixRecipe) {
                BlazeReborn.BREWING_MIX_RECIPES_CACHE.add(brewingMixRecipe);
            }
        });
        BrewingRecipeRegistryAccessor.accessRecipes().addAll(BlazeReborn.BREWING_MIX_RECIPES_CACHE);
    }

}
