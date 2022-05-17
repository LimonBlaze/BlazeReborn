package limonblaze.blazereborn.data.server.builder;

import com.google.gson.JsonObject;
import limonblaze.blazereborn.common.crafting.recipe.BrewingPotionRecipe;
import limonblaze.blazereborn.util.MiscUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BrewingPotionRecipeBuilder {
    private final Potion input;
    private final Potion output;
    private final Ingredient reagent;

    public BrewingPotionRecipeBuilder(Potion input, Potion output, Ingredient reagent) {
        this.input = input;
        this.output = output;
        this.reagent = reagent;
    }

    public BrewingPotionRecipeBuilder(Potion input, Potion output, ItemLike item) {
        this(input, output, Ingredient.of(item));
    }

    public BrewingPotionRecipeBuilder(Potion input, Potion output, Supplier<? extends ItemLike> item) {
        this(input, output, item.get());
    }

    public BrewingPotionRecipeBuilder(Potion input, Potion output, TagKey<Item> tag) {
        this(input, output, Ingredient.of(tag));
    }

    public static BrewingPotionRecipeBuilder basic(Potion result, Ingredient reagent) {
        return new BrewingPotionRecipeBuilder(Potions.AWKWARD, result, reagent);
    }

    public static BrewingPotionRecipeBuilder basic(Potion result, ItemLike item) {
        return new BrewingPotionRecipeBuilder(Potions.AWKWARD, result, item);
    }

    public static BrewingPotionRecipeBuilder basic(Potion result, Supplier<? extends ItemLike> item) {
        return new BrewingPotionRecipeBuilder(Potions.AWKWARD, result, item);
    }

    public static BrewingPotionRecipeBuilder basic(Potion result, TagKey<Item> tag) {
        return new BrewingPotionRecipeBuilder(Potions.AWKWARD, result, tag);
    }

    public static BrewingPotionRecipeBuilder lenthen(Potion basic, Potion lengthened) {
        return new BrewingPotionRecipeBuilder(basic, lengthened, Items.REDSTONE);
    }

    public static BrewingPotionRecipeBuilder strengthen(Potion basic, Potion strengthened) {
        return new BrewingPotionRecipeBuilder(basic, strengthened, Items.GLOWSTONE_DUST);
    }

    public static BrewingPotionRecipeBuilder corrupt(Potion basic, Potion corrupted) {
        return new BrewingPotionRecipeBuilder(basic, corrupted, Items.FERMENTED_SPIDER_EYE);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, input, output, reagent));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Potion input;
        private final Potion output;
        private final Ingredient reagent;

        public Result(ResourceLocation id, Potion input, Potion output, Ingredient reagent) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.reagent = reagent;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("input", MiscUtils.registryName(input).toString());
            json.addProperty("output", MiscUtils.registryName(output).toString());
            json.add("reagent", reagent.toJson());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BrewingPotionRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }

}
