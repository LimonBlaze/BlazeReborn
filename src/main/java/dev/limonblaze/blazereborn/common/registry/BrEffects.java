package dev.limonblaze.blazereborn.common.registry;

import dev.limonblaze.blazereborn.api.BlazeRebornAPI;
import dev.limonblaze.blazereborn.common.effect.SoulPiercingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrEffects {
    
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BlazeRebornAPI.MODID);
    
    public static final RegistryObject<SoulPiercingEffect> SOUL_PIERCING = MOB_EFFECT.register("soul_piercing", SoulPiercingEffect::new);
    
}
