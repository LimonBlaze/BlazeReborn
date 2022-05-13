package limonblaze.blazereborn.common;

import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.extension.FireVariantSourceBlock;
import limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.common.registry.BlazeRebornItems;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BlazeRebornCommon {

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        registerDefaultBlockFireVariants();
        registerDispenserBehavior();
    }

    public void registerDefaultBlockFireVariants() {
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.FIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.SOUL_FIRE, FireVariant.SOUL_FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.CAMPFIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.SOUL_CAMPFIRE, FireVariant.SOUL_FIRE.get());
    }

    public void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(BlazeRebornItems.SOUL_FIRE_CHARGE.get(), VariantedFireChargeItem.createDispenseItemBehavior());
    }

    @SubscribeEvent
    public void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put(BlazeRebornEntityTypes.SOUL_BLAZE.get(), Blaze.createAttributes().build());
    }

}
