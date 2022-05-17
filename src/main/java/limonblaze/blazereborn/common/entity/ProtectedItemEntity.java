package limonblaze.blazereborn.common.entity;

import limonblaze.blazereborn.common.registry.BrEntityTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * A simple {@link ItemEntity} extension that can't be destroyed before it can be picked up<br>
 * Useful for lava fishing<br>
 */
public class ProtectedItemEntity extends ItemEntity {

    public ProtectedItemEntity(EntityType<? extends ProtectedItemEntity> type, Level level) {
        super(type, level);
    }

    public ProtectedItemEntity(Level level, double x, double y, double z, ItemStack stack) {
        this(level, x, y, z, stack, level.random.nextDouble() * 0.2D - 0.1D, 0.2D, level.random.nextDouble() * 0.2D - 0.1D);
    }

    public ProtectedItemEntity(Level level, double x, double y, double z, ItemStack stack, double dx, double dy, double dz) {
        this(BrEntityTypes.PROTECTED_ITEM.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(dx, dy, dz);
        this.setItem(stack);
        this.lifespan = stack.getEntityLifespan(level);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return !this.hasPickUpDelay() && super.hurt(source, amount);
    }

}
