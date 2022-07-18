package dev.limonblaze.blazereborn.api.extension.fire;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.limonblaze.blazereborn.api.registry.BlazeRebornRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
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
    public static final RegistryObject<FireVariant> AIR = BUILTIN_FIRE_VARIANTS.register("air", () -> new SimpleFireVariant(new ResourceLocation("air"), -1, 0));
    public static final RegistryObject<FireVariant> FIRE = BUILTIN_FIRE_VARIANTS.register("fire", () -> new SimpleFireVariant(new ResourceLocation("fire"), 0, 1));
    public static final RegistryObject<FireVariant> SOUL_FIRE = BUILTIN_FIRE_VARIANTS.register("soul_fire", () -> new SimpleFireVariant(new ResourceLocation("soul_fire"), 2, 2));

    private final int priority;
    private final ResourceLocation fireSpriteId0;
    private final ResourceLocation fireSpriteId1;

    @OnlyIn(Dist.CLIENT)
    protected Material fireMaterial0;
    @OnlyIn(Dist.CLIENT)
    protected Material fireMaterial1;

    protected FireVariant(int priority, ResourceLocation id0, ResourceLocation id1) {
        this.priority = priority;
        this.fireSpriteId0 = id0;
        this.fireSpriteId1 = id1;
    }
    
    protected FireVariant(int priority, ResourceLocation id) {
        this(
            priority,
            new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_0"),
            new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_1")
        );
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
    public void renderOnEntity(PoseStack poseStack, MultiBufferSource buffer, Entity entity) {
        //NO-OP
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnScreen(Minecraft minecraft, PoseStack poseStack) {
        //NO-OP
    }

    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getSpriteForSreenEffect(Entity entity) {
        if(fireMaterial1 == null) {
            fireMaterial1 = ForgeHooksClient.getBlockMaterial(fireSpriteId1);
        }
        return fireMaterial1.sprite();
    }

    @OnlyIn(Dist.CLIENT)
    public TextureAtlasSprite getSpriteForEntityRender(Entity entity, boolean flag) {
        if(flag) {
            if(fireMaterial1 == null) {
                fireMaterial1 = ForgeHooksClient.getBlockMaterial(fireSpriteId1);
            }
            return fireMaterial1.sprite();
        } else {
            if(fireMaterial0 == null) {
                fireMaterial0 = ForgeHooksClient.getBlockMaterial(fireSpriteId0);
            }
            return fireMaterial0.sprite();
        }
    }

    @Override
    public int compareTo(@Nonnull FireVariant fireVariant) {
        return Integer.compare(this.priority, fireVariant.priority);
    }
    
}
