package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.block.entity.SoulBrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BlazeRebornAPI.MODID);

    public static final RegistryObject<BlockEntityType<SoulBrewingStandBlockEntity>> SOUL_BREWING_STAND_ENTITY = BLOCK_ENTITY_TYPES.register("soul_brewing_stand", () ->
        BlockEntityType.Builder.of(SoulBrewingStandBlockEntity::new, BrBlocks.SOUL_BREWING_STAND.get()).build(null));

}
