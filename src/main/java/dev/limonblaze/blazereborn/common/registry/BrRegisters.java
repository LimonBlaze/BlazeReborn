package dev.limonblaze.blazereborn.common.registry;

import dev.limonblaze.blazereborn.api.BlazeRebornAPI;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariant;
import dev.limonblaze.blazereborn.api.registry.BlazeRebornRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public class BrRegisters {

    public static final DeferredRegister<FireVariant> FIRE_VARIANTS = DeferredRegister.create(BlazeRebornRegistries.FIRE_VARIANT_KEY, BlazeRebornAPI.MODID);

    public static void register(IEventBus modBus) {
        BlazeRebornRegistries.FIRE_VARIANT = FIRE_VARIANTS.makeRegistry(FireVariant.class, () ->
            new RegistryBuilder<FireVariant>().disableSaving().setDefaultKey(new ResourceLocation("air")));
        FIRE_VARIANTS.register(modBus);
        FireVariant.BUILTIN_FIRE_VARIANTS.register(modBus);
    }

}
