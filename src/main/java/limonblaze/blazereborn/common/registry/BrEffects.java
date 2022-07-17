package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.effect.SimpleEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrEffects {
    
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BlazeRebornAPI.MODID);
    
    public static final RegistryObject<SimpleEffect> SOUL_PIERCING = MOB_EFFECT.register("soul_piercing", () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0x00CCFF));
    
}
