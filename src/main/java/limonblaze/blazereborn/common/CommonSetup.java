package limonblaze.blazereborn.common;

import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.api.extension.fire.FireVariantSourceBlock;
import limonblaze.blazereborn.common.crafting.recipe.BrewingPotionRecipe;
import limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.common.registry.BlazeRebornItems;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    
    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        registerDefaultBlockFireVariants();
        registerDispenserBehavior();
    }

    public static void registerDefaultBlockFireVariants() {
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.FIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.SOUL_FIRE, FireVariant.SOUL_FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.CAMPFIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.SOUL_CAMPFIRE, FireVariant.SOUL_FIRE.get());
    }

    public static void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(BlazeRebornItems.SOUL_FIRE_CHARGE.get(), VariantedFireChargeItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @SubscribeEvent
    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put(BlazeRebornEntityTypes.SOUL_BLAZE.get(), Blaze.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        event.getRegistry().registerAll(BrewingPotionRecipe.SERIALIZER);
    }

}
