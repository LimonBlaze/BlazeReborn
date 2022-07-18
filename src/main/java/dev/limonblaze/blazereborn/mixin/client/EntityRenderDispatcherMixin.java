package dev.limonblaze.blazereborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariant;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantRenderedEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "renderFlame", at = @At("HEAD"), cancellable = true)
    private void blazereborn$cancelRenderFlameIfAir(PoseStack pMatrixStack, MultiBufferSource pBuffer, Entity pEntity, CallbackInfo ci) {
        FireVariant variant = ((FireVariantRenderedEntity)pEntity).getRenderedFireVariant();
        if(variant.hasCustomRender()) {
            variant.renderOnEntity(pMatrixStack, pBuffer, pEntity);
            ci.cancel();
        }
    }

    @ModifyVariable(method = "renderFlame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"), ordinal = 0)
    private TextureAtlasSprite blazereborn$renderFlame0(TextureAtlasSprite sprite, PoseStack pMatrixStack, MultiBufferSource pBuffer, Entity pEntity) {
        return ((FireVariantRenderedEntity)pEntity).getRenderedFireVariant().getSpriteForEntityRender(pEntity, false);
    }

    @ModifyVariable(method = "renderFlame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"), ordinal = 1)
    private TextureAtlasSprite blazereborn$renderFlame1(TextureAtlasSprite sprite, PoseStack pMatrixStack, MultiBufferSource pBuffer, Entity pEntity) {
        return ((FireVariantRenderedEntity)pEntity).getRenderedFireVariant().getSpriteForEntityRender(pEntity, true);
    }

}
