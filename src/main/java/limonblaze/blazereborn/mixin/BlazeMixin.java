package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.common.data.tag.MultiBlazesBiomeTags;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mixin(Blaze.class)
public abstract class BlazeMixin extends Monster {

    protected BlazeMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if(pReason == MobSpawnType.NATURAL && this.getType() == EntityType.BLAZE && pLevel.getBiome(this.blockPosition()).is(MultiBlazesBiomeTags.SOUL_BLAZE_SPAWNING_BIOMES)) {
            this.convertTo(BlazeRebornEntityTypes.SOUL_BLAZE.get(), true);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

}
