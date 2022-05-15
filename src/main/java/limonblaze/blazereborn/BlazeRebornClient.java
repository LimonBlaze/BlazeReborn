package limonblaze.blazereborn;

import limonblaze.blazereborn.client.ClientEventHandler;
import limonblaze.blazereborn.client.renderer.entity.SoulBlazeRenderer;
import limonblaze.blazereborn.client.screen.SoulBrewingStandScreen;
import limonblaze.blazereborn.common.registry.BlazeRebornBlocks;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.common.registry.BlazeRebornMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class BlazeRebornClient {

    public static void init() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(BlazeRebornClient.class);
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
    }

    @SubscribeEvent
    public static void setup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlazeRebornBlocks.SOUL_BREWING_STAND.get(), RenderType.cutout());
        MenuScreens.register(BlazeRebornMenuTypes.SOUL_BREWING_STAND.get(), SoulBrewingStandScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BlazeRebornEntityTypes.SOUL_BLAZE.get(), SoulBlazeRenderer::new);
    }

}
