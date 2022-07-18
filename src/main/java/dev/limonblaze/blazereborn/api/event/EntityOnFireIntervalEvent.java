package dev.limonblaze.blazereborn.api.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * An event fired every tick when the entity is on fire and is checking an interval for recieving damage from vanilla mechanics.<br>
 * Cacelling this will block further change from other event handlers.<br>
 **/
@Cancelable
public class EntityOnFireIntervalEvent extends EntityEvent {

    private int interval;

    public EntityOnFireIntervalEvent(Entity entity, int interval) {
        super(entity);
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

}
