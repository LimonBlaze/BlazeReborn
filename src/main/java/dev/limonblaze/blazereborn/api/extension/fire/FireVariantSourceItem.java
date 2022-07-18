package dev.limonblaze.blazereborn.api.extension.fire;

import dev.limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

/**
 * Implemented in {@link VariantedFireChargeItem} to handle its behavior on setting {@link net.minecraft.world.entity.projectile.SmallFireball}'s fire variant.
 * Implement this to other items, so they can provide their own fire variant handling logic.
 **/
public interface FireVariantSourceItem {

    FireVariant getFireVariant(Entity entity, ItemStack stack);

}
