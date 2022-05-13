package limonblaze.blazereborn;

import limonblaze.blazereborn.client.BlazeRebornClient;
import limonblaze.blazereborn.common.CommonEventHandler;
import limonblaze.blazereborn.common.BlazeRebornCommon;
import limonblaze.blazereborn.common.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlazeReborn.MODID)
public class BlazeReborn {
    public static final String MODID = "blaze_reborn";

    public BlazeReborn() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(new BlazeRebornCommon());
        modBus.register(new BlazeRebornClient());
        BlazeRebornBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
        BlazeRebornBlocks.BLOCKS.register(modBus);
        BlazeRebornEntityTypes.ENTITY_TYPE.register(modBus);
        BlazeRebornItems.ITEMS.register(modBus);
        BlazeRebornMenuTypes.MENU_TYPES.register(modBus);
        BlazeRebornRegisters.register(modBus);
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }

}
