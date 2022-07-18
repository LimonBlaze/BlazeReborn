package dev.limonblaze.blazereborn.api.extension.fire;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class SimpleFireVariant extends FireVariant {
    
    private final int interval;
    private final float multiplier;
    
    public SimpleFireVariant(ResourceLocation id, int priority, int interval, float multiplier) {
        super(priority, id);
        this.interval = interval;
        this.multiplier = multiplier;
    }
    
    public SimpleFireVariant(ResourceLocation id0, ResourceLocation id1, int priority, int interval, float multiplier) {
        super(priority, id0, id1);
        this.interval = interval;
        this.multiplier = multiplier;
    }
    
    public SimpleFireVariant(ResourceLocation id, int priority, float multiplier) {
        this(id, priority, 20, multiplier);
    }
    
    public SimpleFireVariant(ResourceLocation id0, ResourceLocation id1, int priority, float multiplier) {
        this(id0, id1, priority, 20, multiplier);
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
