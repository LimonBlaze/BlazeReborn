package limonblaze.blazereborn.api.extension.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Implemented in {@link BaseFireBlock} and {@link CampfireBlock} to handle the logic of setting entity's {@link FireVariant}.
 * Subclasses of the above 2 classes may implement this and override the logic.
 **/
public interface FireVariantSourceBlock {

    HashMap<Block, FireVariant> DEFAULT_BLOCK_FIRE_VARIANTS = new HashMap<>();

    default FireVariant getFireVariant(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        return DEFAULT_BLOCK_FIRE_VARIANTS.getOrDefault(this, FireVariant.FIRE.get());
    }

}
