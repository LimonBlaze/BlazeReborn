package dev.limonblaze.blazereborn.api.event;

import dev.limonblaze.blazereborn.api.extension.fire.FireVariant;
import dev.limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class EntityFireVariantEvent extends EntityEvent {
    protected final FireVariant fireVariant;
    
    public EntityFireVariantEvent(Entity entity, FireVariant fireVariant) {
        super(entity);
        this.fireVariant = fireVariant;
    }

    public FireVariant getFireVariant() {
        return fireVariant;
    }

    /**
     * An event fired whenever the Entity's {@link FireVariant} is updated from external source (new value differs from the original).<br>
     * Cacelling this will make the {@link FireVariant} stay unchanged.<br>
     **/
    @Cancelable
    public static class Update extends EntityFireVariantEvent {
        private FireVariant newFireVariant;

        public Update(Entity entity, FireVariant originalFireVariant, FireVariant newFireVariant) {
            super(entity, originalFireVariant);
            this.newFireVariant = newFireVariant;
        }

        public FireVariant getOriginalFireVariant() {
            return this.fireVariant;
        }

        public FireVariant getNewFireVariant() {
            return newFireVariant;
        }

        public void setNewFireVariant(FireVariant newFireVariant) {
            this.newFireVariant = newFireVariant;
        }

    }

    /**
     * An event fired when a fire variant related damage is about to apply.<br>
     * Builtin use cases are vanilla on fire damage and small fireball's damage<br>
     * Setting the amount <= 0 will prevent the damage.<br>
     * Cancelling this will block further event handlers from modifying the amount<br>
     **/
    @Cancelable
    public static class Attack extends EntityFireVariantEvent {
        
        private final DamageSource source;
        private float amount;
    
        public Attack(Entity entity, DamageSource source, float amount) {
            this(entity, ((FireVariantHoldingEntity)entity).getFireVariant(), source, amount);
        }
        
        public Attack(Entity entity, FireVariant fireVariant, DamageSource source, float amount) {
            super(entity, fireVariant);
            this.source = source;
            this.amount = amount;
        }
    
        public DamageSource getSource() {
            return source;
        }
    
        public float getAmount() {
            return amount;
        }
    
        public void setAmount(float amount) {
            this.amount = amount;
        }
    
    }

}
