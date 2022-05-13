package limonblaze.blazereborn.common.entity.monster;

import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.extension.FireVariantHoldingEntity;
import limonblaze.blazereborn.common.data.tag.MultiBlazesBiomeTags;
import limonblaze.blazereborn.common.entity.ai.goal.AbstractBlazeAttackGoal;
import limonblaze.blazereborn.common.entity.projectile.VariantedSmallFireball;
import limonblaze.blazereborn.common.registry.BlazeRebornItems;
import limonblaze.blazereborn.util.EntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class SoulBlaze extends Blaze implements FireVariantHoldingEntity {

    public SoulBlaze(EntityType<? extends Blaze> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        EntityUtils.removeGoalFromSelector(this.goalSelector, wrapped -> wrapped.getGoal() instanceof Blaze.BlazeAttackGoal);
        this.goalSelector.addGoal(4, new SoulBlazeAttackGoal(this));
    }

    @Override
    public FireVariant getFireVariant() {
        return FireVariant.SOUL_FIRE.get();
    }

    @Override
    public void setFireVariant(FireVariant fireVariant, boolean force) {
        //NO-OP
    }

    public static boolean canSpawn(EntityType<? extends SoulBlaze> pType, LevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return Monster.checkAnyLightMonsterSpawnRules(pType, pLevel, pReason, pPos, pRandom) &&
            pLevel.getBiome(pPos).is(MultiBlazesBiomeTags.SOUL_BLAZE_SPAWNING_BIOMES);
    }

    public static class SoulBlazeAttackGoal extends AbstractBlazeAttackGoal<SoulBlaze> {

        public SoulBlazeAttackGoal(SoulBlaze soulBlaze) {
            super(soulBlaze);
        }

        @Override
        protected void launchAttack(Level level, SoulBlaze blaze, double offsetX, double offsetY, double offsetZ) {
            VariantedSmallFireball soulSmallFireball = new VariantedSmallFireball(level, blaze, offsetX, offsetY, offsetZ);
            soulSmallFireball.setItem(new ItemStack(BlazeRebornItems.SOUL_FIRE_CHARGE.get()));
            soulSmallFireball.setPos(soulSmallFireball.getX(), blaze.getY(0.5D) + 0.5D, soulSmallFireball.getZ());
            level.addFreshEntity(soulSmallFireball);
        }

    }

}
