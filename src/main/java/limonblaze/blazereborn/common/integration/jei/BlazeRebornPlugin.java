package limonblaze.blazereborn.common.integration.jei;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.client.screen.SoulBrewingStandScreen;
import limonblaze.blazereborn.common.menu.SoulBrewingStandMenu;
import limonblaze.blazereborn.common.registry.BrItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class BlazeRebornPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return BlazeRebornAPI.id("common");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(new PotionMixRecipeConverter(registration.getVanillaRecipeFactory()).convertToJeiBrewingRecipes(), VanillaRecipeCategoryUid.BREWING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BrItems.SOUL_BREWING_STAND.get()), VanillaRecipeCategoryUid.BREWING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SoulBrewingStandScreen.class, 97, 16, 14, 30, VanillaRecipeCategoryUid.BREWING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(SoulBrewingStandMenu.class, VanillaRecipeCategoryUid.BREWING, 0, 4, 5, 36);
    }

}
