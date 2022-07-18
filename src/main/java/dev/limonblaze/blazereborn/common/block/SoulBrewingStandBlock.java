package dev.limonblaze.blazereborn.common.block;

import dev.limonblaze.blazereborn.common.block.entity.SoulBrewingStandBlockEntity;
import dev.limonblaze.blazereborn.common.registry.BrBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import java.util.Random;

public class SoulBrewingStandBlock extends BrewingStandBlock {
    public static final BooleanProperty HAS_BOTTLE_3 = BooleanProperty.create("has_bottle_3");
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2, HAS_BOTTLE_3};
    
    public SoulBrewingStandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(HAS_BOTTLE[0], false)
            .setValue(HAS_BOTTLE[1], false)
            .setValue(HAS_BOTTLE[2], false)
            .setValue(HAS_BOTTLE[3], false)
        );
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        double d0 = (double)pPos.getX() + 0.4D + (double)pRand.nextFloat() * 0.2D;
        double d1 = (double)pPos.getY() + 0.7D + (double)pRand.nextFloat() * 0.3D;
        double d2 = (double)pPos.getZ() + 0.4D + (double)pRand.nextFloat() * 0.2D;
        pLevel.addParticle(ParticleTypes.SOUL, d0, d1, d2, 0.0D, 0.1D, 0.0D);
    }
    
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_BOTTLE);
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