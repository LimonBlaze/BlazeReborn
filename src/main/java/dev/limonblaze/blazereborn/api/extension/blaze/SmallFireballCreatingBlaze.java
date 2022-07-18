package dev.limonblaze.blazereborn.api.extension.blaze;

import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;

import javax.annotation.Nonnull;

/**
 * Implemented in {@link Blaze} to handle the {@link SmallFireball} shot by {@link Blaze.BlazeAttackGoal}<br>
 * Blaze's subclasses may implement this to override the behavior<br>
 **/
public interface SmallFireballCreatingBlaze {
    
    SmallFireball createSmallFireball(@Nonnull SmallFireball defaultFireball);
    
}