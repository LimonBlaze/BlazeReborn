package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.loot.function.ConditionedSmeltingItemFunction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class BrLoots {

    public static LootItemFunctionType CONDITIONED_SMELTING = new LootItemFunctionType(new ConditionedSmeltingItemFunction.Serializer());

    public static void register() {
        register("conditioned_smelting", CONDITIONED_SMELTING);
    }

    private static void register(String name, LootItemFunctionType type) {
        Registry.register(Registry.LOOT_FUNCTION_TYPE, BlazeRebornAPI.id(name), type);
    }

}
