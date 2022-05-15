package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
    private void blazereborn$setFireType(EntityHitResult entityHit, CallbackInfo ci) {
        ((FireVariantHoldingEntity)entityHit.getEntity()).setFireVariant(((FireVariantHoldingEntity)this).getFireVariant(), false);
    }

}
