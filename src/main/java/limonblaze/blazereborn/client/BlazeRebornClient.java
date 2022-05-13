package limonblaze.blazereborn.client;

import limonblaze.blazereborn.client.renderer.entity.SoulBlazeRenderer;
import limonblaze.blazereborn.client.screen.SoulBrewingStandScreen;
import limonblaze.blazereborn.common.registry.BlazeRebornBlocks;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.common.registry.BlazeRebornMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class BlazeRebornClient {

    @SubscribeEvent
    public void setup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlazeRebornBlocks.SOUL_BREWING_STAND.get(), RenderType.cutout());
        MenuScreens.register(BlazeRebornMenuTypes.SOUL_BREWING_STAND.get(), SoulBrewingStandScreen::new);
    }

    @SubscribeEvent
    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BlazeRebornEntityTypes.VARIANTED_SMALL_FIREBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(BlazeRebornEntityTypes.SOUL_BLAZE.get(), SoulBlazeRenderer::new);
    }

}
