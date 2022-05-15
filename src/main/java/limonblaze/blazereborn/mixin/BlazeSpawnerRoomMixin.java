package limonblaze.blazereborn.mixin;

import limonblaze.blazereborn.api.event.BlazeSpawnerModificationEvent;
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
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NetherBridgePieces.MonsterThrone.class)
public abstract class BlazeSpawnerRoomMixin extends StructurePiece {
    private WorldGenLevel blazereborn$cachedLevel = null;
    private BlockPos blazereborn$cachedSpawnerPos = null;

    protected BlazeSpawnerRoomMixin(StructurePieceType pType, int pGenDepth, BoundingBox pBox) {
        super(pType, pGenDepth, pBox);
    }

    @Inject(method = "postProcess", at = @At("HEAD"))
    private void blazereborn$cacheStructureLevelAndPos(WorldGenLevel pLevel, StructureFeatureManager pStructureFeatureManager, ChunkGenerator pChunkGenerator, Random pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos, CallbackInfo ci) {
        blazereborn$cachedLevel = pLevel;
        blazereborn$cachedSpawnerPos = this.getWorldPos(3, 5, 5);
    }

    @ModifyArg(method = "postProcess", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BaseSpawner;setEntityId(Lnet/minecraft/world/entity/EntityType;)V"), index = 0)
    private EntityType<?> blazereborn$setSpawnerEntityType(EntityType<?> entityType) {
        if(blazereborn$cachedLevel != null && blazereborn$cachedSpawnerPos != null) {
            BlazeSpawnerModificationEvent event = new BlazeSpawnerModificationEvent(blazereborn$cachedLevel, blazereborn$cachedSpawnerPos, entityType);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getType();
        }
        return entityType;
    }

}
