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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class ContainerMixRecipe extends BrewingMixRecipe<Item> {
    public static final RecipeType<ContainerMixRecipe> TYPE = RecipeType.register(BlazeRebornAPI.id("brewing_container").toString());

    public ContainerMixRecipe(ResourceLocation id, PotionBrewing.Mix<Item> mix) {
        super(id, mix);
    }

    @Override
    public boolean isInput(ItemStack input) {
        return input.is(mix.from.get());
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
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height == 2;
    }

    @Override
    public ItemStack getResultItem() {
        return mix.to.get().getDefaultInstance();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BlazeRebornRecipeSerializers.CONTAINER_MIX.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ContainerMixRecipe> {

        @Override
        public ContainerMixRecipe fromJson(ResourceLocation recipeId, JsonObject jsonObject) {
            ResourceLocation inputId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "input"));
            Item input = ForgeRegistries.ITEMS.getValue(inputId);
            if(input == null) throw new JsonSyntaxException("Unknown input item with id: [" + inputId + "] found in recipe json [" + recipeId + "] !");
            ResourceLocation outputId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "output"));
            Item output = ForgeRegistries.ITEMS.getValue(outputId);
            if(output == null) throw new JsonSyntaxException("Unknown output item with id: [" + inputId + "] found in recipe json [" + recipeId + "] !");
            Ingredient reagent = Ingredient.fromJson(jsonObject.get("reagent"));
            return new ContainerMixRecipe(recipeId, new PotionBrewing.Mix<>(input, reagent, output));
        }

        @Nullable
        @Override
        public ContainerMixRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
            ResourceLocation inputId = buf.readResourceLocation();
            Item input = ForgeRegistries.ITEMS.getValue(inputId);
            if(input == null) throw new JsonSyntaxException("Unknown input item with id: [" + inputId + "] received from network in recipe [" + recipeId + "] !");
            ResourceLocation outputId = buf.readResourceLocation();
            Item output = ForgeRegistries.ITEMS.getValue(outputId);
            if(output == null) throw new JsonSyntaxException("Unknown output item with id: [" + inputId + "] received from network in recipe [" + recipeId + "] !");
            Ingredient reagent = Ingredient.fromNetwork(buf);
            return new ContainerMixRecipe(recipeId, new PotionBrewing.Mix<>(input, reagent, output));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ContainerMixRecipe recipe) {
            buf.writeResourceLocation(recipe.mix.from.name());
            buf.writeResourceLocation(recipe.mix.to.name());
            recipe.mix.ingredient.toNetwork(buf);
        }

    }

}