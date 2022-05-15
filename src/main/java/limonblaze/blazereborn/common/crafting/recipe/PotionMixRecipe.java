package limonblaze.blazereborn.common.crafting.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.registry.BlazeRebornRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class PotionMixRecipe extends BrewingMixRecipe<Potion> {
    public static final RecipeType<PotionMixRecipe> TYPE = RecipeType.register(BlazeRebornAPI.id("brewing_potion").toString());

    public PotionMixRecipe(ResourceLocation id, PotionBrewing.Mix<Potion> mix) {
        super(id, mix);
    }

    @Override
    public boolean isInput(ItemStack input) {
        return PotionBrewing.ALLOWED_CONTAINER.test(input) && PotionUtils.getPotion(input) == mix.from.get();
    }

    @Override
    public boolean isIngredient(ItemStack reagent) {
        return mix.ingredient.test(reagent);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack reagent) {
        SimpleContainer container = new SimpleContainer(reagent, input);
        return this.matches(container, null) ? this.assemble(container) : ItemStack.EMPTY;
    }

    @Override
    public boolean matches(Container container, @Nullable Level level) {
        return isIngredient(container.getItem(0)) && isInput(container.getItem(1));
    }

    @Override
    public ItemStack assemble(Container container) {
        return PotionUtils.setPotion(container.getItem(1).copy(), mix.to.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height == 2;
    }

    /**
     * For potion reference only, result item makes no sense for unknown input
     **/
    @Override
    public ItemStack getResultItem() {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), mix.to.get());
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BlazeRebornRecipeSerializers.POTION_MIX.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PotionMixRecipe> {

        @Override
        public PotionMixRecipe fromJson(ResourceLocation recipeId, JsonObject jsonObject) {
            ResourceLocation inputId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "input"));
            Potion input = ForgeRegistries.POTIONS.getValue(inputId);
            if(input == null) throw new JsonSyntaxException("Unknown input potion with id: [" + inputId + "] found in recipe json [" + recipeId + "] !");
            ResourceLocation outputId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "output"));
            Potion output = ForgeRegistries.POTIONS.getValue(outputId);
            if(output == null) throw new JsonSyntaxException("Unknown output potion with id: [" + inputId + "] found in recipe json [" + recipeId + "] !");
            Ingredient reagent = Ingredient.fromJson(jsonObject.get("reagent"));
            return new PotionMixRecipe(recipeId, new PotionBrewing.Mix<>(input, reagent, output));
        }

        @Nullable
        @Override
        public PotionMixRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
            ResourceLocation inputId = buf.readResourceLocation();
            Potion input = ForgeRegistries.POTIONS.getValue(inputId);
            if(input == null) throw new JsonSyntaxException("Unknown input potion with id: [" + inputId + "] received from network in recipe [" + recipeId + "] !");
            ResourceLocation outputId = buf.readResourceLocation();
            Potion output = ForgeRegistries.POTIONS.getValue(outputId);
            if(output == null) throw new JsonSyntaxException("Unknown output potion with id: [" + inputId + "] received from network in recipe [" + recipeId + "] !");
            Ingredient reagent = Ingredient.fromNetwork(buf);
            return new PotionMixRecipe(recipeId, new PotionBrewing.Mix<>(input, reagent, output));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, PotionMixRecipe recipe) {
            buf.writeResourceLocation(recipe.mix.from.name());
            buf.writeResourceLocation(recipe.mix.to.name());
            recipe.mix.ingredient.toNetwork(buf);
        }

    }

}
