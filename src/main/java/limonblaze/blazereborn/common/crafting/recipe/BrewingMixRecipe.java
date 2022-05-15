package limonblaze.blazereborn.common.crafting.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class BrewingMixRecipe<T extends ForgeRegistryEntry<T>> implements Recipe<Container>, IBrewingRecipe {
    protected final ResourceLocation id;
    protected final PotionBrewing.Mix<T> mix;

    public BrewingMixRecipe(ResourceLocation id, PotionBrewing.Mix<T> mix) {
        this.id = id;
        this.mix = mix;
    }

}
