package limonblaze.blazereborn.common.entity.projectile;

import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.extension.FireVariantHoldingEntity;
import limonblaze.blazereborn.api.extension.FireVariantSourceItem;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import limonblaze.blazereborn.mixin.access.AbstractFireballEntityAccessor;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class VariantedSmallFireball extends Fireball implements FireVariantHoldingEntity {

    public VariantedSmallFireball(EntityType<? extends VariantedSmallFireball> entityType, Level level) {
        super(entityType, level);
    }

    public VariantedSmallFireball(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ) {
        super(BlazeRebornEntityTypes.VARIANTED_SMALL_FIREBALL.get(), shooter, offsetX, offsetY, offsetZ, level);
    }

    public VariantedSmallFireball(Level level, double x, double y, double z, double offsetX, double offsetY, double offsetZ) {
        super(BlazeRebornEntityTypes.VARIANTED_SMALL_FIREBALL.get(), x, y, z, offsetX, offsetY, offsetZ, level);
    }

    public void setItem(ItemStack stack) {
        if(stack.getItem() != Items.FIRE_CHARGE || stack.hasTag()) {
            this.getEntityData().set(AbstractFireballEntityAccessor.getDataItemStack(), Util.make(stack.copy(), stack1 -> stack1.setCount(1)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
    }

    public FireVariant getFireVariant() {
        ItemStack stack = this.getItem();
        if(stack.getItem() instanceof FireVariantSourceItem fvs) {
            return fvs.getFireVariant(this, stack);
        }
        return FireVariant.FIRE.get();
    }

    @Override
    public void setFireVariant(FireVariant fireVariant, boolean force) {
        //NO-OP
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity target = result.getEntity();
            if (!target.fireImmune()) {
                Entity owner = this.getOwner();
                int i = target.getRemainingFireTicks();
                target.setSecondsOnFire(5);
                FireVariant newFireVariant = this.getFireVariant();
                DamageSource damageSource = DamageSource.fireball(this, owner);
                boolean flag = target.hurt(damageSource, newFireVariant.modifyFireDamage(target, damageSource, 5.0F));
                if(flag) {
                    ((FireVariantHoldingEntity)target).setFireVariant(newFireVariant, false);
                    if (owner instanceof LivingEntity) {
                        this.doEnchantDamageEffects((LivingEntity)owner, target);
                    }
                } else {
                    target.setRemainingFireTicks(i);
                }
            }
        }
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level.isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner())) {
                BlockPos blockpos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level.isEmptyBlock(blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level, blockpos));
                }
            }
        }
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

}
