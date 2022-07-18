package dev.limonblaze.blazereborn.compat;

import dev.limonblaze.blazereborn.BlazeReborn;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariant;
import dev.limonblaze.blazereborn.api.extension.fire.SimpleFireVariant;
import dev.limonblaze.blazereborn.api.registry.BlazeRebornRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CompatFireVariants {
    
    public static final DeferredRegister<FireVariant> FIRECRAFTING_REGISTER = DeferredRegister.create(BlazeRebornRegistries.FIRE_VARIANT_KEY, BlazeReborn.FIRECRAFTING_MODID);
    
    public static final RegistryObject<FireVariant> ENDER = FIRECRAFTING_REGISTER.register( "ender", () ->
        new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "enderfire/soul_fire"), 3, 3));
    public static final RegistryObject<FireVariant> HELL = FIRECRAFTING_REGISTER.register( "hell", () ->
        new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "hellfire/soul_fire"), 3, 3));
    public static final RegistryObject<FireVariant> HEAVEN = FIRECRAFTING_REGISTER.register( "heaven", () ->
        new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "heavenfire/soul_fire"), 3, 3));
    public static final RegistryObject<FireVariant> THREE_MEIS_TRUE = FIRECRAFTING_REGISTER.register( "three_meis_true",
        () -> new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "threemeistruefire/soul_fire"), 3, 3));
    public static final RegistryObject<FireVariant> MAGIC = FIRECRAFTING_REGISTER.register( "magic",
        () -> new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "magicfire/soul_fire"), 3, 3));
    public static final RegistryObject<FireVariant> DRAGON_BREATH = FIRECRAFTING_REGISTER.register( "dragon_breath",
        () -> new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "dragonbreathfire/soul_fire"), 3, 3));
    public static final RegistryObject<FireVariant> COMPANION = FIRECRAFTING_REGISTER.register( "companion",
        () -> new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "companionfire/soul_fire"), 1, 0));
    public static final RegistryObject<FireVariant> RAINBOW = FIRECRAFTING_REGISTER.register( "rainbow",
        () -> new SimpleFireVariant(new ResourceLocation(BlazeReborn.FIRECRAFTING_MODID, "rainbowfire/soul_fire"), 1, 1));
    
}
