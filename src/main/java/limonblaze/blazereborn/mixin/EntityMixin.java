package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.extension.FireVariantHoldingEntity;
import limonblaze.blazereborn.api.event.EntityUpdateFireVariantEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(Entity.class)
public abstract class EntityMixin implements FireVariantHoldingEntity {

    @Shadow @Final protected SynchedEntityData entityData;
    @Shadow private int remainingFireTicks;

    @Shadow public Level level;

    @Shadow public abstract boolean isOnFire();

    @Unique private static final String FIRE_VARIANT_KEY = "FireVariant";
    @Unique private static final EntityDataAccessor<String> FIRE_VARIANT = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.STRING);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mulbl$defineFireVariant(EntityType<?> pType, Level pLevel, CallbackInfo ci) {
        this.entityData.define(FIRE_VARIANT, FireVariant.AIR.get().toString());
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;"))
    private void mulbl$saveFireVariant(CompoundTag pCompound, CallbackInfoReturnable<CompoundTag> cir) {
        pCompound.putString(FIRE_VARIANT_KEY, this.getFireVariant().getId().toString());
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;hasUUID(Ljava/lang/String;)Z"))
    private void mulbl$loadFireVariant(CompoundTag pCompound, CallbackInfo ci) {
        FireVariant variant = FireVariant.getFromId(pCompound.getString(FIRE_VARIANT_KEY));
        this.mulbl$setFireVariantInternal(this.isOnFire() && variant.isAir() ? this.getDefaultBurningFireVariant() : variant);
    }

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void mulbl$tickFireVariant(CallbackInfo ci) {
        if(!this.level.isClientSide) {
            if(!this.isOnFire()) {
                if(!this.getFireVariant().isAir()) {
                    this.mulbl$setFireVariantInternal(FireVariant.AIR.get());
                }
            } else if(this.getFireVariant().isAir()) {
                this.setFireVariant(this.getDefaultBurningFireVariant(), false);
            }
        }
    }

    private void mulbl$setFireVariantInternal(@Nonnull FireVariant fireVariant) {
        this.entityData.set(FIRE_VARIANT, fireVariant.getId().toString());
    }

    @Override
    public @Nonnull FireVariant getFireVariant() {
        return FireVariant.getFromId(this.entityData.get(FIRE_VARIANT));
    }

    @Override
    public void setFireVariant(@Nonnull FireVariant newFireVariant, boolean force) {
        if(!this.level.isClientSide) {
            FireVariant originalFireVariant = this.getFireVariant();
            int flag = newFireVariant.compareTo(originalFireVariant);
            if(flag != 0 && (force || flag > 0)) {
                EntityUpdateFireVariantEvent event = new EntityUpdateFireVariantEvent(((Entity)(Object)this), originalFireVariant, newFireVariant);
                if(!MinecraftForge.EVENT_BUS.post(event)) {
                    this.mulbl$setFireVariantInternal(newFireVariant);
                }
            }
        }
    }

}
