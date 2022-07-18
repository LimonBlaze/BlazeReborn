package dev.limonblaze.blazereborn.common.registry;

import dev.limonblaze.blazereborn.api.BlazeRebornAPI;
import dev.limonblaze.blazereborn.common.block.SoulBrewingStandBlock;
import dev.limonblaze.blazereborn.common.block.SoulMagmaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlazeRebornAPI.MODID);

    public static final RegistryObject<SoulMagmaBlock> SOUL_MAGMA_BLOCK = BLOCKS.register("soul_magma_block", () ->
        new SoulMagmaBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DIAMOND)
            .requiresCorrectToolForDrops()
            .lightLevel((p_152684_) -> 3)
            .randomTicks()
            .strength(0.5F)
            .isValidSpawn((state, level, pos, type) -> type.fireImmune())
            .hasPostProcess(BrBlocks::always).
            emissiveRendering(BrBlocks::always)
        )
    );
    public static final RegistryObject<SoulBrewingStandBlock> SOUL_BREWING_STAND = BLOCKS.register("soul_brewing_stand", () ->
        new SoulBrewingStandBlock(BlockBehaviour.Properties.of(Material.METAL)
            .requiresCorrectToolForDrops()
            .strength(0.5F)
            .lightLevel(state -> 1)
            .noOcclusion())
    );
    
    private static boolean always(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
    
}
