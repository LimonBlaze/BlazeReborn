package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.api.extension.blaze.SmallFireballHoldingBlaze;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nonnull;

@Mixin(Blaze.class)
public class BlazeMixin implements SmallFireballHoldingBlaze {

    @Override
    @Nonnull
    public SmallFireball createSmallFireball(@Nonnull SmallFireball defaultFireball) {
        return defaultFireball;
    }

}
