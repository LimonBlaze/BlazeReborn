package limonblaze.blazereborn.api.extension;

import net.minecraft.world.entity.Entity;

/**
 * Implemented in {@link Entity} to handle it's {@link FireVariant}
 * */
public interface FireVariantHoldingEntity {

    FireVariant getFireVariant();

    void setFireVariant(FireVariant fireVariant, boolean force);

    /**
     * @return {@link FireVariant}<br>
     * Used as a fallback when an {@link Entity} is set on fire through {@link Entity#setRemainingFireTicks(int)}, by default it's {@link FireVariant#FIRE}.
     * */
    default FireVariant getDefaultBurningFireVariant() {
        return FireVariant.FIRE.get();
    }

}
