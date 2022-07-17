package limonblaze.blazereborn.common;

import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.api.extension.fire.FireVariantSourceBlock;
import limonblaze.blazereborn.common.entity.monster.SoulMagmaCube;
import limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import limonblaze.blazereborn.common.registry.BrEffects;
import limonblaze.blazereborn.common.registry.BrEntityTypes;
import limonblaze.blazereborn.common.registry.BrItems;
import limonblaze.blazereborn.common.registry.BrPotions;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    
    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        registerDefaultBlockFireVariants();
        registerDispenserBehaviors();
        registerSpawnPlacements();
        registerBrewingRecipes();
    }

    public static void registerDefaultBlockFireVariants() {
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.FIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.SOUL_FIRE, FireVariant.SOUL_FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.CAMPFIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.put(Blocks.SOUL_CAMPFIRE, FireVariant.SOUL_FIRE.get());
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(BrItems.SOUL_FIRE_CHARGE.get(), VariantedFireChargeItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(BrEntityTypes.SOUL_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(BrEntityTypes.SOUL_MAGMA_CUBE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SoulMagmaCube::checkSpawnRules);
    }
    
    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.STRONG_STRENGTH, BrItems.SOUL_BLAZE_POWDER.get(), BrPotions.SOUL_PIERCING.get());
        PotionBrewing.addMix(BrPotions.SOUL_PIERCING.get(), Items.REDSTONE, BrPotions.LONG_SOUL_PIERCING.get());
        PotionBrewing.addMix(Potions.AWKWARD, BrItems.SOUL_MAGMA_CREAM.get(), BrPotions.RESISTANCE.get());
        PotionBrewing.addMix(BrPotions.RESISTANCE.get(), Items.GLOWSTONE_DUST, BrPotions.STRONG_RESISTANCE.get());
        PotionBrewing.addMix(BrPotions.RESISTANCE.get(), Items.REDSTONE, BrPotions.LONG_RESISTANCE.get());
    }

    @SubscribeEvent
    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put(BrEntityTypes.SOUL_BLAZE.get(), Blaze.createAttributes().build());
        event.put(BrEntityTypes.SOUL_MAGMA_CUBE.get(), MagmaCube.createAttributes().build());
    }

}
