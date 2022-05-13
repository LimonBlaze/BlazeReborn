package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.common.block.SoulBrewingStandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlazeRebornBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlazeReborn.MODID);

    public static final RegistryObject<SoulBrewingStandBlock> SOUL_BREWING_STAND = BLOCKS.register("soul_brewing_stand", () ->
        new SoulBrewingStandBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(0.5F).lightLevel(state -> 1).noOcclusion()));

}
