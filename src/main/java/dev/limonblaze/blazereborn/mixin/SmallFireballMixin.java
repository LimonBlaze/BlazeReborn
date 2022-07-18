package dev.limonblaze.blazereborn.mixin;

import dev.limonblaze.blazereborn.api.event.EntityFireVariantEvent;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariant;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantSourceItem;
import dev.limonblaze.blazereborn.mixin.access.AbstractFireballEntityAccessor;
import net.minecraft.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nonnull;

@Mixin(SmallFireball.class)
public class SmallFireballMixin extends Fireball implements FireVariantHoldingEntity {

    public SmallFireballMixin(EntityType<? extends Fireball> type, Level level) {
        super(type, level);
    }

    public void setItem(ItemStack stack) {
        if(stack.getItem() != Items.FIRE_CHARGE || stack.hasTag()) {
            this.getEntityData().set(AbstractFireballEntityAccessor.getDataItemStack(), Util.make(stack.copy(), stack1 -> stack1.setCount(1)));
        }
    }

    @Nonnull
    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
    }

    @Nonnull
    public FireVariant getFireVariant() {
        ItemStack stack = this.getItem();
        if(stack.getItem() instanceof FireVariantSourceItem item) {
            return item.getFireVariant(this, stack);
        }
        return FireVariant.FIRE.get();
    }

    @Override
    public void setFireVariant(@Nonnull FireVariant fireVariant, boolean force) {
        //NO-OP
    }

    @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public boolean blazereborn$hurtEntityWithFireVariant(Entity entity, DamageSource source, float amount) {
        boolean flag = false;
        FireVariant fireVariant = this.getFireVariant();
        EntityFireVariantEvent.Attack event = new EntityFireVariantEvent.Attack(entity, fireVariant, source, amount);
        if(!MinecraftForge.EVENT_BUS.post(event)) {
            float newAmount = event.getAmount();
            flag = newAmount > 0 && entity.hurt(event.getSource(), event.getAmount());
        }
        if(flag) {
            ((FireVariantHoldingEntity)entity).setFireVariant(fireVariant, false);
        }
        return flag;
    }

}
