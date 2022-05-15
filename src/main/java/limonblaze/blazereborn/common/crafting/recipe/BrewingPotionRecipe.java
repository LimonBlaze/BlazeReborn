package limonblaze.blazereborn.common.crafting.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import limonblaze.blazereborn.api.BlazeRebornAPI;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class BrewingPotionRecipe implements Recipe<Container>, IBrewingRecipe {
    public static final ResourceLocation ID = BlazeRebornAPI.id("brewing_potion");
    public static final Serializer SERIALIZER = new Serializer(ID);
    public static final RecipeType<BrewingPotionRecipe> TYPE = RecipeType.register(ID.toString());

    protected final ResourceLocation id;
    protected final PotionBrewing.Mix<Potion> mix;

    public BrewingPotionRecipe(ResourceLocation id, PotionBrewing.Mix<Potion> mix) {
        this.id = id;
        this.mix = mix;
    }

    public PotionBrewing.Mix<Potion> getMix() {
        return this.mix;
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
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BrewingPotionRecipe> {

        public Serializer(ResourceLocation id) {
            this.setRegistryName(id);
        }

        @Override
        public BrewingPotionRecipe fromJson(ResourceLocation recipeId, JsonObject jsonObject) {
            ResourceLocation inputId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "input"));
            Potion input = ForgeRegistries.POTIONS.getValue(inputId);
            if(input == null) throw new JsonSyntaxException("Unknown input potion with id: [" + inputId + "] found in recipe json [" + recipeId + "] !");
            ResourceLocation outputId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "output"));
            Potion output = ForgeRegistries.POTIONS.getValue(outputId);
            if(output == null) throw new JsonSyntaxException("Unknown output potion with id: [" + inputId + "] found in recipe json [" + recipeId + "] !");
            Ingredient reagent = Ingredient.fromJson(jsonObject.get("reagent"));
            return new BrewingPotionRecipe(recipeId, new PotionBrewing.Mix<>(input, reagent, output));
        }

        @Nullable
        @Override
        public BrewingPotionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
            ResourceLocation inputId = buf.readResourceLocation();
            Potion input = ForgeRegistries.POTIONS.getValue(inputId);
            if(input == null) throw new JsonSyntaxException("Unknown input potion with id: [" + inputId + "] received from network in recipe [" + recipeId + "] !");
            ResourceLocation outputId = buf.readResourceLocation();
            Potion output = ForgeRegistries.POTIONS.getValue(outputId);
            if(output == null) throw new JsonSyntaxException("Unknown output potion with id: [" + inputId + "] received from network in recipe [" + recipeId + "] !");
            Ingredient reagent = Ingredient.fromNetwork(buf);
            return new BrewingPotionRecipe(recipeId, new PotionBrewing.Mix<>(input, reagent, output));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BrewingPotionRecipe recipe) {
            buf.writeResourceLocation(recipe.mix.from.name());
            buf.writeResourceLocation(recipe.mix.to.name());
            recipe.mix.ingredient.toNetwork(buf);
        }

    }

}
