package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.extension.FireVariantSourceBlock;
import limonblaze.blazereborn.api.extension.FireVariantHoldingEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
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

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(CampfireBlock.class)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class CampfireBlockMixin extends BaseEntityBlock implements FireVariantSourceBlock {

    protected CampfireBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void mulbl$setFireVariant(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity, CallbackInfo ci) {
        if(pEntity.isOnFire()) {
            ((FireVariantHoldingEntity)pEntity).setFireVariant(this.getFireVariant(pEntity, pState, pLevel, pPos), false);
        }
    }

    @Override
    public FireVariant getFireVariant(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        return FireVariantSourceBlock.DEFAULT_BLOCK_FIRE_VARIANTS.getOrDefault(this, FireVariant.FIRE.get());
    }

}
