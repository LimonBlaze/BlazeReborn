package limonblaze.blazereborn.common.menu;

import limonblaze.blazereborn.common.registry.BrItems;
import limonblaze.blazereborn.common.registry.BrMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SoulBrewingStandMenu extends AbstractContainerMenu {
    private static final int BOTTLE_SLOT_START = 0;
    private static final int BOTTLE_SLOT_END = 3;
    private static final int INGREDIENT_SLOT = 4;
    private static final int FUEL_SLOT = 5;
    private static final int INV_SLOT_START = 6;
    private static final int INV_SLOT_END = 33;
    private static final int USE_ROW_SLOT_START = 33;
    private static final int USE_ROW_SLOT_END = 42;
    private final Container brewingStand;
    private final ContainerData brewingStandData;
    private final Slot ingredientSlot;
    
    public SoulBrewingStandMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new SimpleContainer(6), new SimpleContainerData(2));
    }
    
    public SoulBrewingStandMenu(int syncId, Inventory inventory, Container container, ContainerData data) {
        super(BrMenuTypes.SOUL_BREWING_STAND.get(), syncId);
        checkContainerSize(container, 6);
        checkContainerDataCount(data, 2);
        this.brewingStand = container;
        this.brewingStandData = data;
        this.addSlot(new BrewingStandMenu.PotionSlot(container, 0, 38, 51));
        this.addSlot(new BrewingStandMenu.PotionSlot(container, 1, 64, 58));
        this.addSlot(new BrewingStandMenu.PotionSlot(container, 2, 94, 58));
        this.addSlot(new BrewingStandMenu.PotionSlot(container, 3, 120, 51));
        this.ingredientSlot = this.addSlot(new BrewingStandMenu.IngredientsSlot(container, 4, 79, 17));
        this.addSlot(new FuelSlot(container, 5, 17, 17));
        this.addDataSlots(data);
        
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
        
    }
    
    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(Player pPlayer) {
        return this.brewingStand.stillValid(pPlayer);
    }
    
    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if ((index < BOTTLE_SLOT_START || index > BOTTLE_SLOT_END) && index != INGREDIENT_SLOT && index != FUEL_SLOT) {
                if (FuelSlot.mayPlaceItem(itemstack)) {
                    if (this.moveItemStackTo(itemstack1, FUEL_SLOT, FUEL_SLOT + 1, false) || this.ingredientSlot.mayPlace(itemstack1) && !this.moveItemStackTo(itemstack1, INGREDIENT_SLOT, INGREDIENT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.ingredientSlot.mayPlace(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, INGREDIENT_SLOT, INGREDIENT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (BrewingStandMenu.PotionSlot.mayPlaceItem(itemstack)) {
                    if (!this.moveItemStackTo(itemstack1, BOTTLE_SLOT_START, BOTTLE_SLOT_END + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= INV_SLOT_START && index < INV_SLOT_END) {
                    if (!this.moveItemStackTo(itemstack1, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= USE_ROW_SLOT_START && index < USE_ROW_SLOT_END) {
                    if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                
                slot.onQuickCraft(itemstack1, itemstack);
            }
            
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            
            slot.onTake(player, itemstack1);
        }
        
        return itemstack;
    }
    
    public int getFuel() {
        return this.brewingStandData.get(1);
    }
    
    public int getBrewingTicks() {
        return this.brewingStandData.get(0);
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
