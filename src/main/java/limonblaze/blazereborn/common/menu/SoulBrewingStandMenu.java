package limonblaze.blazereborn.common.menu;

import limonblaze.blazereborn.common.registry.BrItems;
import limonblaze.blazereborn.common.registry.BrMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SoulBrewingStandMenu extends BrewingStandMenu {

    public SoulBrewingStandMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new SimpleContainer(5), new SimpleContainerData(2));
    }

    public SoulBrewingStandMenu(int syncId, Inventory inventory, Container container, ContainerData data) {
        super(syncId, inventory, container, data);
        this.slots.set(4, new FuelSlot(container, 4, 17, 17));
    }

    @Override
    public MenuType<?> getType() {
        return BrMenuTypes.SOUL_BREWING_STAND.get();
    }

    static class FuelSlot extends Slot {
        public FuelSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        public boolean mayPlace(ItemStack pStack) {
            return mayPlaceItem(pStack);
        }

        public static boolean mayPlaceItem(ItemStack pItemStack) {
            return pItemStack.is(BrItems.SOUL_BLAZE_POWDER.get());
        }

        public int getMaxStackSize() {
            return 64;
        }
    }

}
