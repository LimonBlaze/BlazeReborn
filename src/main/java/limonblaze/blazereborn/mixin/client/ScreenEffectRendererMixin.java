package limonblaze.blazereborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import limonblaze.blazereborn.api.extension.FireVariantRenderedEntity;
import limonblaze.blazereborn.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private static void mulbl$cancelRenderFireIfAir(Minecraft pMinecraft, PoseStack pPoseStack, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && ((FireVariantRenderedEntity)player).getRenderedFireVariant().isAir()) {
            ci.cancel();
        }
    }

    @ModifyVariable(method = "renderFire", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"), name = "textureatlassprite")
    private static TextureAtlasSprite mulbl$renderFire(TextureAtlasSprite sprite) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null) {
            ClientUtils.getSpriteForFireVariant(((FireVariantRenderedEntity)player).getRenderedFireVariant(), true);
        }
        return sprite;
    }

}
