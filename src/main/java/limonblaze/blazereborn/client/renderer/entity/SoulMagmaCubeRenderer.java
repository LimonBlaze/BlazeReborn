package limonblaze.blazereborn.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.entity.monster.SoulMagmaCube;
import net.minecraft.client.model.LavaSlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoulMagmaCubeRenderer extends MobRenderer<SoulMagmaCube, LavaSlimeModel<SoulMagmaCube>> {

    public static final ResourceLocation SOUL_MAGMA_CUBE_LOCATION = BlazeRebornAPI.id("textures/entity/soul_magma_cube.png");

    public SoulMagmaCubeRenderer(EntityRendererProvider.Context context) {
        super(context, new LavaSlimeModel<>(context.bakeLayer(ModelLayers.MAGMA_CUBE)), 0.25F);
    }

    @Override
    protected int getBlockLightLevel(SoulMagmaCube pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SoulMagmaCube pEntity) {
        return SOUL_MAGMA_CUBE_LOCATION;
    }

    @Override
    protected void scale(SoulMagmaCube pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        int i = pLivingEntity.getSize();
        float f = Mth.lerp(pPartialTickTime, pLivingEntity.oSquish, pLivingEntity.squish) / (i * 0.5F + 1.0F);
        float f1 = 1.0F / (f + 1.0F);
        pMatrixStack.scale(f1 * (float)i, 1.0F / f1 * i, f1 * i);
    }

}
