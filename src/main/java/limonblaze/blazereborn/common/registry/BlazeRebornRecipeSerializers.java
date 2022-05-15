package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.crafting.recipe.ContainerMixRecipe;
import limonblaze.blazereborn.common.crafting.recipe.PotionMixRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlazeRebornRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BlazeRebornAPI.MODID);

    public static final RegistryObject<ContainerMixRecipe.Serializer> CONTAINER_MIX = RECIPE_SERIALIZERS.register("brewing_container", ContainerMixRecipe.Serializer::new);
    public static final RegistryObject<PotionMixRecipe.Serializer> POTION_MIX = RECIPE_SERIALIZERS.register("brewing_potion", PotionMixRecipe.Serializer::new);

}
