package limonblaze.blazereborn.api.extension;

import limonblaze.blazereborn.common.entity.projectile.VariantedSmallFireball;
import limonblaze.blazereborn.common.item.VariantedFireChargeItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

/**
 * Implemented in {@link VariantedFireChargeItem} to handle it's behavior on setting {@link VariantedSmallFireball}'s fire variant.
 * Implement this to other items so they can provide their own fire variant handling logic.
 * */
public interface FireVariantSourceItem {

    FireVariant getFireVariant(Entity entity, ItemStack stack);

}
