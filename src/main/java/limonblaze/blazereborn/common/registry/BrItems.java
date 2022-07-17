package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BlazeRebornAPI.MODID);

    public static final RegistryObject<SpawnEggItem> SOUL_BLAZE_SPAWN_EGG = ITEMS.register("soul_blaze_spawn_egg", () ->
        new ForgeSpawnEggItem(BrEntityTypes.SOUL_BLAZE, 0x00DDFF, 0x0088AA, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> SOUL_MAGMA_CUBE_SPAWN_EGG = ITEMS.register("soul_magma_cube_spawn_egg", () ->
        new ForgeSpawnEggItem(BrEntityTypes.SOUL_MAGMA_CUBE, 0x0088AA, 0x004488, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SOUL_BLAZE_ROD = ITEMS.register("soul_blaze_rod", () ->
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SOUL_BLAZE_POWDER = ITEMS.register("soul_blaze_powder", () ->
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<Item> SOUL_MAGMA_CREAM = ITEMS.register("soul_magma_cream", () ->
        new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<BlockItem> SOUL_MAGMA_BLOCK = ITEMS.register("soul_magma_block", () ->
        new BlockItem(BrBlocks.SOUL_MAGMA_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<VariantedFireChargeItem> SOUL_FIRE_CHARGE = ITEMS.register("soul_fire_charge", () ->
        new VariantedFireChargeItem(FireVariant.SOUL_FIRE, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<BlockItem> SOUL_BREWING_STAND = ITEMS.register("soul_brewing_stand", () ->
        new BlockItem(BrBlocks.SOUL_BREWING_STAND.get(), new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    
}