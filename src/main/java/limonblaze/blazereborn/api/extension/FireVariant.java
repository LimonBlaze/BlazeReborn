package limonblaze.blazereborn.api.extension;

import limonblaze.blazereborn.api.registry.BlazeRebornRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class FireVariant extends ForgeRegistryEntry<FireVariant> implements Comparable<FireVariant> {
    public static final DeferredRegister<FireVariant> BUILTIN_FIRE_VARIANTS = DeferredRegister.create(BlazeRebornRegistries.FIRE_VARIANT_KEY, "minecraft");
    public static final RegistryObject<FireVariant> AIR = BUILTIN_FIRE_VARIANTS.register("air", () -> new FireVariant.Constant(0, 0));
    public static final RegistryObject<FireVariant> FIRE = BUILTIN_FIRE_VARIANTS.register("fire", () -> new FireVariant.Constant(1, 1));
    public static final RegistryObject<FireVariant> SOUL_FIRE = BUILTIN_FIRE_VARIANTS.register("soul_fire", () -> new FireVariant.Constant(2, 2));

    private final int priority;

    private FireVariant(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public abstract float modifyFireDamage(Entity entity, DamageSource source, float amount);

    public boolean isAir() {
        return this == AIR.get();
    }

    public ResourceLocation getId() {
        return Objects.requireNonNull(this.getRegistryName());
    }

    public static FireVariant getFromId(String id) {
        ResourceLocation parsedId = ResourceLocation.tryParse(id);
        return parsedId == null ? AIR.get() : getFromId(parsedId);
    }

    public static FireVariant getFromId(ResourceLocation id) {
        FireVariant fv = BlazeRebornRegistries.FIRE_VARIANT.get().getValue(id);
        return fv == null ? AIR.get() : fv;
    }

    @Override
    public int compareTo(@Nonnull FireVariant fireVariant) {
        return Integer.compare(this.priority, fireVariant.priority);
    }

    public static class Constant extends FireVariant {
        private final float damageMultiplier;

        private Constant(int priority, float damageMultiplier) {
            super(priority);
            this.damageMultiplier = damageMultiplier;
        }

        @Override
        public float modifyFireDamage(Entity entity, DamageSource source, float amount) {
            return amount * damageMultiplier;
        }

    }

}
