package dev.limonblaze.blazereborn.mixin;

import dev.limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public class ZombieMixin extends Monster {

    protected ZombieMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Inject(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
    private void blazereborn$setFireType(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ((FireVariantHoldingEntity)entity).setFireVariant(((FireVariantHoldingEntity)this).getFireVariant(), false);
    }
    
}
