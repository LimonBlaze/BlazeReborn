package limonblaze.blazereborn.client.renderer.entity;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.entity.monster.SoulBlaze;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoulBlazeRenderer extends MobRenderer<SoulBlaze, BlazeModel<SoulBlaze>> {

    private static final ResourceLocation SOUL_BLAZE_LOCATION = BlazeRebornAPI.id("textures/entity/soul_blaze.png");

    public SoulBlazeRenderer(EntityRendererProvider.Context context) {
        super(context, new BlazeModel<>(context.bakeLayer(ModelLayers.BLAZE)), 0.5F);
    }

    @Override
    protected int getBlockLightLevel(SoulBlaze soulBlaze, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SoulBlaze soulBlaze) {
        return SOUL_BLAZE_LOCATION;
    }

}
