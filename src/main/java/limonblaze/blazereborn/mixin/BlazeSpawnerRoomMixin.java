package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.common.data.tag.MultiBlazesBiomeTags;
import limonblaze.blazereborn.common.registry.BlazeRebornEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.NetherBridgePieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NetherBridgePieces.MonsterThrone.class)
public abstract class BlazeSpawnerRoomMixin extends StructurePiece {
    private boolean mulbl$shouldSpawnSoulBlaze = false;

    protected BlazeSpawnerRoomMixin(StructurePieceType pType, int pGenDepth, BoundingBox pBox) {
        super(pType, pGenDepth, pBox);
    }

    @Inject(method = "postProcess", at = @At("HEAD"))
    private void mulbl$checkShouldSpawnSoulBlaze(WorldGenLevel pLevel, StructureFeatureManager pStructureFeatureManager, ChunkGenerator pChunkGenerator, Random pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos, CallbackInfo ci) {
        if(pLevel.getBiome(this.getWorldPos(3, 5, 5)).is(MultiBlazesBiomeTags.SOUL_BLAZE_SPAWNING_BIOMES)) {
            this.mulbl$shouldSpawnSoulBlaze = true;
        }
    }

    @ModifyArg(method = "postProcess", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BaseSpawner;setEntityId(Lnet/minecraft/world/entity/EntityType;)V"), index = 0)
    private EntityType<?> mulbl$setSpawnerForSoulBlaze(EntityType<?> entityType) {
        return this.mulbl$shouldSpawnSoulBlaze ? BlazeRebornEntityTypes.SOUL_BLAZE.get() : entityType;
    }

}
