package dev.limonblaze.blazereborn.mixin.access;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Fireball.class)
public interface AbstractFireballEntityAccessor {

    @Accessor(value = "DATA_ITEM_STACK", remap = false)
    static EntityDataAccessor<ItemStack> getDataItemStack() {
        throw new AssertionError();
    }

}
