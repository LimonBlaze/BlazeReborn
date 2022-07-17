package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrPotions {
    
    public static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTIONS, BlazeRebornAPI.MODID);
    
    public static final RegistryObject<Potion> LONG_RESISTANCE = POTION.register("resistance", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 9600)));
    public static final RegistryObject<Potion> STRONG_RESISTANCE = POTION.register("strong_resistance", () -> new Potion("resistance",new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 1)));
    public static final RegistryObject<Potion> RESISTANCE = POTION.register("long_resistance", () -> new Potion("resistance",new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600)));
    public static final RegistryObject<Potion> LONG_SOUL_PIERCING = POTION.register("long_soul_piercing", () -> new Potion("soul_piercing", new MobEffectInstance(BrEffects.SOUL_PIERCING.get(), 3600)));
    public static final RegistryObject<Potion> SOUL_PIERCING = POTION.register("soul_piercing", () -> new Potion(new MobEffectInstance(BrEffects.SOUL_PIERCING.get(), 1800)));

}
