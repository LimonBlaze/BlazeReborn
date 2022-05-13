package limonblaze.blazereborn.api.event;

import limonblaze.blazereborn.api.extension.FireVariant;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * An event fired on {@link limonblaze.blazereborn.mixin.EntityMixin#setFireVariant(FireVariant, boolean)}, server side,
 * if the FireType is updated (new value differs from the original).<br>
 * Cacelling this will make the FireType unchanged.<br>
 * Use {@link EntityUpdateFireVariantEvent#setNewFireVariant(FireVariant)} to set the new FireType.
 * */
@Cancelable
public class EntityUpdateFireVariantEvent extends EntityEvent {
    private final FireVariant originalFireVariant;
    private FireVariant newFireVariant;

    public EntityUpdateFireVariantEvent(Entity entity, FireVariant originalFireVariant, FireVariant newFireVariant) {
        super(entity);
        this.originalFireVariant = originalFireVariant;
        this.newFireVariant = newFireVariant;
    }

    public FireVariant getOriginalFireVariant() {
        return originalFireVariant;
    }

    public FireVariant getNewFireVariant() {
        return newFireVariant;
    }

    public void setNewFireVariant(FireVariant newFireVariant) {
        this.newFireVariant = newFireVariant;
    }

}
