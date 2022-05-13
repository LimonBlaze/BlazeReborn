package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.registry.BlazeRebornRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public class BlazeRebornRegisters {

    public static final DeferredRegister<FireVariant> FIRE_VARIANTS = DeferredRegister.create(BlazeRebornRegistries.FIRE_VARIANT_KEY, BlazeReborn.MODID);

    public static void register(IEventBus modBus) {
        BlazeRebornRegistries.FIRE_VARIANT = FIRE_VARIANTS.makeRegistry(FireVariant.class, () ->
            new RegistryBuilder<FireVariant>().disableSaving().setDefaultKey(new ResourceLocation("air")));
        FIRE_VARIANTS.register(modBus);
        FireVariant.BUILTIN_FIRE_VARIANTS.register(modBus);
    }

}
