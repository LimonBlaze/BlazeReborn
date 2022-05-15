package limonblaze.blazereborn.api.extension.fire;

import limonblaze.blazereborn.api.registry.BlazeRebornRegistries;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FireVariant extends ForgeRegistryEntry<FireVariant> implements Comparable<FireVariant> {
    public static final DeferredRegister<FireVariant> BUILTIN_FIRE_VARIANTS = DeferredRegister.create(BlazeRebornRegistries.FIRE_VARIANT_KEY, "minecraft");
    public static final RegistryObject<FireVariant> AIR = BUILTIN_FIRE_VARIANTS.register("air", () -> new Simple(0, 0));
    public static final RegistryObject<FireVariant> FIRE = BUILTIN_FIRE_VARIANTS.register("fire", () -> new Simple(1, 1));
    public static final RegistryObject<FireVariant> SOUL_FIRE = BUILTIN_FIRE_VARIANTS.register("soul_fire", () -> new Simple(2, 2));

    private final float priority;
    protected TextureAtlasSprite fireSprite0;
    protected TextureAtlasSprite fireSprite1;

    private FireVariant(int priority) {
        this.priority = priority;
    }

    public float getPriority() {
        return this.priority;
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

    public int damageInterval(int originalInterval) {
        return 20;
    }

    public float onFireDamage(Entity entity, DamageSource source, float amount) {
        return amount;
    }

    public boolean isAir() {
        return this == AIR.get();
    }

    public boolean hasCustomRender() {
        return this.isAir();
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnEntity() {
        //NO-OP
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnScreen() {
        //NO-OP
    }

    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getSpriteForSreenEffect(Entity entity) {
        if(fireSprite1 == null) {
            fireSprite1 = ForgeHooksClient.getBlockMaterial(new ResourceLocation(this.getId().getNamespace(), "block/" + this.getId().getPath() + "_1")).sprite();
        }
        return fireSprite1;
    }

    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getSpriteForEntityRender(Entity entity, boolean flag) {
        if(flag) {
            if(fireSprite1 == null) {
                fireSprite1 = ForgeHooksClient.getBlockMaterial(new ResourceLocation(this.getId().getNamespace(), "block/" + this.getId().getPath() + "_1")).sprite();
            }
            return fireSprite1;
        } else {
            if(fireSprite0 == null) {
                fireSprite0 = ForgeHooksClient.getBlockMaterial(new ResourceLocation(this.getId().getNamespace(), "block/" + this.getId().getPath() + "_0")).sprite();
            }
            return fireSprite0;
        }
    }

    @Override
    public int compareTo(@Nonnull FireVariant fireVariant) {
        return Float.compare(this.priority, fireVariant.priority);
    }

    public static class Simple extends FireVariant {

        private final int interval;
        private final float multiplier;

        public Simple(int priority, int interval, float multiplier) {
            super(priority);
            this.interval = interval;
            this.multiplier = multiplier;
        }

        public Simple(int priority, float multiplier) {
            this(priority, 20, multiplier);
        }

        @Override
        public int damageInterval(int originalInterval) {
            return this.interval;
        }

        @Override
        public float onFireDamage(Entity entity, DamageSource source, float amount) {
            return amount * multiplier;
        }

    }

}
