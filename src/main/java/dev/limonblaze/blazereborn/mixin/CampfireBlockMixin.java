package dev.limonblaze.blazereborn.mixin;

import dev.limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantSourceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock implements FireVariantSourceBlock {

    protected CampfireBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void blazereborn$setFireVariant(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity, CallbackInfo ci) {
        if(pEntity.isOnFire()) {
            ((FireVariantHoldingEntity)pEntity).setFireVariant(this.getFireVariant(pEntity, pState, pLevel, pPos), false);
        }
    }

}
