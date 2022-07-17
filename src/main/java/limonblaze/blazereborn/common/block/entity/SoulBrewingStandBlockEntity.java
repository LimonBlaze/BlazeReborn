package limonblaze.blazereborn.common.block.entity;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.common.block.SoulBrewingStandBlock;
import limonblaze.blazereborn.common.menu.SoulBrewingStandMenu;
import limonblaze.blazereborn.common.registry.BrBlockEntityTypes;
import limonblaze.blazereborn.common.registry.BrItems;
import limonblaze.blazereborn.util.MiscUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Arrays;

public class SoulBrewingStandBlockEntity extends BrewingStandBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{4};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 3, 4};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 3, 5};

    public SoulBrewingStandBlockEntity(BlockPos pWorldPosition, BlockState pState) {
        super(pWorldPosition, pState);
        this.items = NonNullList.withSize(6, ItemStack.EMPTY);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BrBlockEntityTypes.SOUL_BREWING_STAND_ENTITY.get();
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent(MiscUtils.createTranslation("container", "soul_brewing_stand"));
    }

    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new SoulBrewingStandMenu(pId, pPlayer, this, this.dataAccess);
    }
    
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex == 4) {
            return BrewingRecipeRegistry.isValidIngredient(pStack);
        } else if (pIndex == 5) {
            return pStack.is(BrItems.SOUL_BLAZE_POWDER.get());
        } else {
            return BrewingRecipeRegistry.isValidInput(pStack) && this.getItem(pIndex).isEmpty();
        }
    }
    
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.UP) {
            return SLOTS_FOR_UP;
        } else {
            return pSide == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
        }
    }
    
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return pIndex != 4 || pStack.is(Items.GLASS_BOTTLE);
    }
    
    public static void serverTick(Level level, BlockPos pos, BlockState state, SoulBrewingStandBlockEntity brewingStand) {
        ItemStack itemstack = brewingStand.items.get(5);
        if (brewingStand.fuel <= 0 && itemstack.is(BrItems.SOUL_BLAZE_POWDER.get())) {
            brewingStand.fuel = 20;
            itemstack.shrink(1);
            setChanged(level, pos, state);
        }
        
        boolean flag = isBrewable(brewingStand.items);
        boolean flag1 = brewingStand.brewTime > 0;
        ItemStack ingredient = brewingStand.items.get(4);
        if (flag1) {
            --brewingStand.brewTime;
            boolean flag2 = brewingStand.brewTime == 0;
            if (flag2 && flag) {
                doBrew(level, pos, brewingStand.items);
                setChanged(level, pos, state);
            } else if (!flag || !ingredient.is(brewingStand.ingredient)) {
                brewingStand.brewTime = 0;
                setChanged(level, pos, state);
            }
        } else if (flag && brewingStand.fuel > 0) {
            --brewingStand.fuel;
            brewingStand.brewTime = 200;
            brewingStand.ingredient = ingredient.getItem();
            setChanged(level, pos, state);
        }
        
        boolean[] potionBits = brewingStand.getPotionBits();
        if (!Arrays.equals(potionBits, brewingStand.lastPotionCount)) {
            brewingStand.lastPotionCount = potionBits;
            BlockState blockstate = state;
            if (!(state.getBlock() instanceof SoulBrewingStandBlock)) {
                return;
            }
            
            for(int i = 0; i < SoulBrewingStandBlock.HAS_BOTTLE.length; ++i) {
                blockstate = blockstate.setValue(SoulBrewingStandBlock.HAS_BOTTLE[i], potionBits[i]);
            }
            
            level.setBlock(pos, blockstate, 2);
        }
        
    }
    
    /**
     * @return an array of size 4 where every element represents whether the respective slot is not empty
     */
    public boolean[] getPotionBits() {
        boolean[] aboolean = new boolean[4];
        
        for(int i = 0; i < 4; ++i) {
            if (!this.items.get(i).isEmpty()) {
                aboolean[i] = true;
            }
        }
        
        return aboolean;
    }
    
    public static boolean isBrewable(NonNullList<ItemStack> inventory) {
        ItemStack ingredient = inventory.get(4);
        if (!ingredient.isEmpty()) return BrewingRecipeRegistry.canBrew(inventory, ingredient, SLOTS_FOR_SIDES);
        if (ingredient.isEmpty()) {
            return false;
        } else if (!PotionBrewing.isIngredient(ingredient)) {
            return false;
        } else {
            for(int i = 0; i < 4; ++i) {
                ItemStack potion = inventory.get(i);
                if (!potion.isEmpty() && PotionBrewing.hasMix(potion, ingredient)) {
                    return true;
                }
            }
            
            return false;
        }
    }
    
    public static void doBrew(Level level, BlockPos pos, NonNullList<ItemStack> inventory) {
        if(ForgeEventFactory.onPotionAttemptBrew(inventory)) return;
        ItemStack itemstack = inventory.get(4);
        BrewingRecipeRegistry.brewPotions(inventory, itemstack, SLOTS_FOR_SIDES);
        ForgeEventFactory.onPotionBrewed(inventory);
        
        if (itemstack.hasContainerItem()) {
            ItemStack container = itemstack.getContainerItem();
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                itemstack = container;
            } else {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), container);
            }
        } else itemstack.shrink(1);
        
        inventory.set(4, itemstack);
        level.levelEvent(1035, pos, 0);
    }

}