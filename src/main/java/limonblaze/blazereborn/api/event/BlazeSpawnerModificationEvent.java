package limonblaze.blazereborn.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * An event fired on the post process of the blaze spawner room structure piece.<br>
 * Can be used to change the entity type in the spawner<br>
 * Cacelling this will block further change from other event handlers.<br>
 **/
@Cancelable
public class BlazeSpawnerModificationEvent extends Event {

    private final WorldGenLevel level;
    private final BlockPos pos;
    private EntityType<?> type;

    public BlazeSpawnerModificationEvent(WorldGenLevel level, BlockPos pos, EntityType<?> type) {
        this.level = level;
        this.pos = pos;
        this.type = type;
    }

    public WorldGenLevel getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EntityType<?> getType() {
        return type;
    }

    public void setType(EntityType<?> type) {
        this.type = type;
    }

}
