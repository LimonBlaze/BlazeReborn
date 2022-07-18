package dev.limonblaze.blazereborn;

import dev.limonblaze.blazereborn.api.BlazeRebornAPI;
import dev.limonblaze.blazereborn.common.CommonEventHandler;
import dev.limonblaze.blazereborn.common.CommonSetup;
import dev.limonblaze.blazereborn.common.registry.*;
import dev.limonblaze.blazereborn.compat.CompatFireVariants;
import dev.limonblaze.blazereborn.util.BlazeRebornConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(BlazeRebornAPI.MODID)
public class BlazeReborn {
    public static final Logger LOGGER = LoggerFactory.getLogger("Blaze Reborn");
    public static final String FIRECRAFTING_MODID = "firecrafting";
    
    public BlazeReborn() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        BrBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
        BrBlocks.BLOCKS.register(modBus);
        BrEntityTypes.ENTITY_TYPE.register(modBus);
        BrItems.ITEMS.register(modBus);
        BrMenuTypes.MENU_TYPES.register(modBus);
        BrEffects.MOB_EFFECT.register(modBus);
        BrPotions.POTION.register(modBus);
        BrRegisters.register(modBus);
        
        ModList modList = ModList.get();
        if(modList.isLoaded(FIRECRAFTING_MODID)) {
            CompatFireVariants.FIRECRAFTING_REGISTER.register(modBus);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, BlazeRebornConfig.SERVER_SPEC);

        modBus.register(CommonSetup.class);
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> BlazeRebornClient::init);

        LOGGER.info("Blaze Reborn has initialized, ready to set your Minecraft on fire!");
    }

}
