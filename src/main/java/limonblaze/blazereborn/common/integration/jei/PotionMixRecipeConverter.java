package limonblaze.blazereborn.common.integration.jei;

import limonblaze.blazereborn.common.crafting.recipe.BrewingPotionRecipe;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotionMixRecipeConverter {

    private final RecipeManager recipeManager;
    private final IVanillaRecipeFactory vanillaRecipeFactory;

    public PotionMixRecipeConverter(IVanillaRecipeFactory vanillaRecipeFactory) {
        ClientLevel level = Minecraft.getInstance().level;
        if(level == null) throw new IllegalStateException("Unable to get RecipeManager on instantize BrewingMixRecipeMaker because client level is null!");
        this.recipeManager  = level.getRecipeManager();
        this.vanillaRecipeFactory = vanillaRecipeFactory;
    }

    public List<IJeiBrewingRecipe> convertToJeiBrewingRecipes() {
        List<IJeiBrewingRecipe> jeiRecipes = new ArrayList<>();
        List<BrewingPotionRecipe> recipes = recipeManager.getAllRecipesFor(BrewingPotionRecipe.TYPE);
        List<ItemStack> containers = PotionBrewing.ALLOWED_CONTAINERS.stream()
            .flatMap(potionItem -> Arrays.stream(potionItem.getItems()))
            .toList();

        for(BrewingPotionRecipe recipe : recipes) {
            PotionBrewing.Mix<Potion> mix = recipe.getMix();
            Potion inPotion = mix.from.get();
            Potion outPotion = mix.to.get();
            List<ItemStack> reagents = Arrays.asList(mix.ingredient.getItems());

            for(ItemStack container : containers) {
                jeiRecipes.add(vanillaRecipeFactory.createBrewingRecipe(
                    reagents,
                    PotionUtils.setPotion(container.copy(), inPotion),
                    PotionUtils.setPotion(container.copy(), outPotion)
                ));
            }
        }

        return jeiRecipes;
    }

}
