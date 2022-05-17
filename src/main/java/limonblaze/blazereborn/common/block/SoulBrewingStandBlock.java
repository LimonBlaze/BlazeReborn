package limonblaze.blazereborn.common.block;

import limonblaze.blazereborn.common.block.entity.SoulBrewingStandBlockEntity;
import limonblaze.blazereborn.common.registry.BrBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Random;

public class SoulBrewingStandBlock extends BrewingStandBlock {

    public SoulBrewingStandBlock(Properties properties) {
        super(properties);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        double d0 = (double)pPos.getX() + 0.4D + (double)pRand.nextFloat() * 0.2D;
        double d1 = (double)pPos.getY() + 0.7D + (double)pRand.nextFloat() * 0.3D;
        double d2 = (double)pPos.getZ() + 0.4D + (double)pRand.nextFloat() * 0.2D;
        pLevel.addParticle(ParticleTypes.SOUL, d0, d1, d2, 0.0D, 0.1D, 0.0D);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SoulBrewingStandBlockEntity(pPos, pState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BrBlockEntityTypes.SOUL_BREWING_STAND_ENTITY.get(), SoulBrewingStandBlockEntity::serverTick);
    }

}