package dev.limonblaze.blazereborn.mixin;

import dev.limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    private boolean blazereborn$shouldApplyFireVariant = false;

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
    private void blazereborn$setAndCacheFireVariant(EntityHitResult entityHit, CallbackInfo ci) {
        blazereborn$shouldApplyFireVariant = true;
    }

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemainingFireTicks(I)V"))
    private void blazereborn$recoverFireVariant(EntityHitResult entityHit, CallbackInfo ci) {
        blazereborn$shouldApplyFireVariant = false;
    }

    @Inject(method = "onHitEntity", at = @At("TAIL"))
    private void blazereborn$applyFireVariant(EntityHitResult entityHit, CallbackInfo ci) {
        if(blazereborn$shouldApplyFireVariant) {
            FireVariantHoldingEntity target = (FireVariantHoldingEntity)entityHit.getEntity();
            target.setFireVariant(((FireVariantHoldingEntity)this).getFireVariant(), false);
            blazereborn$shouldApplyFireVariant = false;
        }
    }

}
