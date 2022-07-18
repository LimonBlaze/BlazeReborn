package dev.limonblaze.blazereborn.common.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class SoulMagmaCube extends MagmaCube {

    public SoulMagmaCube(EntityType<? extends SoulMagmaCube> type, Level level) {
        super(type, level);
    }

    protected float getAttackDamage() {
        return super.getAttackDamage() + 2.0F;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    public static boolean checkSpawnRules(EntityType<SoulMagmaCube> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
    }

}
