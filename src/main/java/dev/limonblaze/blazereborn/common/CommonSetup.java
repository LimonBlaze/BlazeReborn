package dev.limonblaze.blazereborn.common;

import cn.anecansaitin.firecrafting.common.block.ModBlocks;
import dev.limonblaze.blazereborn.BlazeReborn;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariant;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantSourceBlock;
import dev.limonblaze.blazereborn.common.entity.monster.SoulMagmaCube;
import dev.limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import dev.limonblaze.blazereborn.common.registry.BrEntityTypes;
import dev.limonblaze.blazereborn.common.registry.BrItems;
import dev.limonblaze.blazereborn.common.registry.BrPotions;
import dev.limonblaze.blazereborn.compat.CompatFireVariants;
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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    
    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        registerDispenserBehaviors();
        registerSpawnPlacements();
        registerBrewingRecipes();
        registerBlockFireVariants();
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(BrItems.SOUL_FIRE_CHARGE.get(), VariantedFireChargeItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(BrEntityTypes.SOUL_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(BrEntityTypes.SOUL_MAGMA_CUBE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SoulMagmaCube::checkSpawnRules);
    }
    
    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, BrItems.SOUL_BLAZE_POWDER.get(), BrPotions.SOUL_PIERCING.get());
        PotionBrewing.addMix(BrPotions.SOUL_PIERCING.get(), Items.GLOWSTONE_DUST, BrPotions.STRONG_SOUL_PIERCING.get());
        PotionBrewing.addMix(BrPotions.SOUL_PIERCING.get(), Items.REDSTONE, BrPotions.LONG_SOUL_PIERCING.get());
        PotionBrewing.addMix(Potions.AWKWARD, BrItems.SOUL_MAGMA_CREAM.get(), BrPotions.RESISTANCE.get());
        PotionBrewing.addMix(BrPotions.RESISTANCE.get(), Items.GLOWSTONE_DUST, BrPotions.STRONG_RESISTANCE.get());
        PotionBrewing.addMix(BrPotions.RESISTANCE.get(), Items.REDSTONE, BrPotions.LONG_RESISTANCE.get());
    }
    
    public static void registerBlockFireVariants() {
        FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(Blocks.FIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(Blocks.CAMPFIRE, FireVariant.FIRE.get());
        FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(Blocks.SOUL_FIRE, FireVariant.SOUL_FIRE.get());
        FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(Blocks.SOUL_CAMPFIRE, FireVariant.SOUL_FIRE.get());
        
        ModList modList = ModList.get();
        if(modList.isLoaded(BlazeReborn.FIRECRAFTING_MODID)) {
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.ENDER_FIRE.get(), CompatFireVariants.ENDER.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.HELL_FIRE.get(), CompatFireVariants.HELL.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.HEAVEN_FIRE.get(), CompatFireVariants.HEAVEN.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.THREE_MEIS_TRUE_FIRE.get(), CompatFireVariants.THREE_MEIS_TRUE.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.MAGIC_FIRE.get(), CompatFireVariants.MAGIC.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.DRAGON_BREATH_FIRE.get(), CompatFireVariants.DRAGON_BREATH.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.COMPANION_FIRE.get(), CompatFireVariants.COMPANION.get());
            FireVariantSourceBlock.BLOCK_FIRE_VARIANT_MAP.put(ModBlocks.RAINBOW_FIRE.get(), CompatFireVariants.RAINBOW.get());
        }
    }

    @SubscribeEvent
    public static void registerMobAttributes(EntityAttributeCreationEvent event) {
        event.put(BrEntityTypes.SOUL_BLAZE.get(), Blaze.createAttributes().build());
        event.put(BrEntityTypes.SOUL_MAGMA_CUBE.get(), MagmaCube.createAttributes().build());
    }

}
