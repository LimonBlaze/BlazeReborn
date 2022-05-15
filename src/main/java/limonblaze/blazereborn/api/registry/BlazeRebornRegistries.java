package limonblaze.blazereborn.api.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.api.extension.fire.FireVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class BlazeRebornRegistries {

    public static Supplier<IForgeRegistry<FireVariant>> FIRE_VARIANT;
    public static final ResourceKey<Registry<FireVariant>> FIRE_VARIANT_KEY = ResourceKey.createRegistryKey(BlazeRebornAPI.id("fire_variant"));

}
