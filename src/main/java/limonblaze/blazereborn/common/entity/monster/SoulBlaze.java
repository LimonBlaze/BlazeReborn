package limonblaze.blazereborn.common.entity.monster;

import limonblaze.blazereborn.api.extension.blaze.SmallFireballCreatingBlaze;
import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import limonblaze.blazereborn.common.registry.BrItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulBlaze extends Blaze implements FireVariantHoldingEntity, SmallFireballCreatingBlaze {

    public SoulBlaze(EntityType<? extends SoulBlaze> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    public FireVariant getFireVariant() {
        return FireVariant.SOUL_FIRE.get();
    }

    @Override
    public void setFireVariant(FireVariant fireVariant, boolean force) {
        //NO-OP
    }

    @Override
    public SmallFireball createSmallFireball(SmallFireball defaultFireball) {
        defaultFireball.setItem(new ItemStack(BrItems.SOUL_FIRE_CHARGE.get()));
        return defaultFireball;
    }

}
