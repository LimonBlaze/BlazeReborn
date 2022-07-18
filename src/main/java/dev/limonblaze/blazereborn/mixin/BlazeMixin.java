package dev.limonblaze.blazereborn.mixin;

import dev.limonblaze.blazereborn.api.extension.blaze.SmallFireballCreatingBlaze;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;

@Mixin(Blaze.class)
public class BlazeMixin implements SmallFireballCreatingBlaze {

    @Override
    @Nonnull
    public SmallFireball createSmallFireball(@Nonnull SmallFireball defaultFireball) {
        return defaultFireball;
    }

}
