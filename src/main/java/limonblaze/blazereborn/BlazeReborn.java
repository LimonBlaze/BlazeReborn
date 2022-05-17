package limonblaze.blazereborn;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.CommonEventHandler;
import limonblaze.blazereborn.common.CommonSetup;
import limonblaze.blazereborn.common.crafting.recipe.BrewingPotionRecipe;
import limonblaze.blazereborn.common.registry.*;
import limonblaze.blazereborn.util.BlazeRebornConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Mod(BlazeRebornAPI.MODID)
public class BlazeReborn {
    public static final Logger LOGGER = LoggerFactory.getLogger("Blaze Reborn");
    public static final Set<BrewingPotionRecipe> BREWING_POTION_RECIPES = new HashSet<>();

    public BlazeReborn() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        BrBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
        BrBlocks.BLOCKS.register(modBus);
        BrEntityTypes.ENTITY_TYPE.register(modBus);
        BrItems.ITEMS.register(modBus);
        BrMenuTypes.MENU_TYPES.register(modBus);
        BrRegisters.register(modBus);
        BrLoots.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, BlazeRebornConfig.SERVER_SPEC);

        modBus.register(CommonSetup.class);
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> BlazeRebornClient::init);

        LOGGER.info("Blaze Reborn has initialized, ready to set your Minecraft on fire!");
    }

}
