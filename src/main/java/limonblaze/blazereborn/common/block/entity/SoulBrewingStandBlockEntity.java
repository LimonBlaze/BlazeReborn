package limonblaze.blazereborn.common.block.entity;

import limonblaze.blazereborn.common.crafting.recipe.ContainerMixRecipe;
import limonblaze.blazereborn.common.menu.SoulBrewingStandMenu;
import limonblaze.blazereborn.common.registry.BlazeRebornBlockEntityTypes;
import limonblaze.blazereborn.common.registry.BlazeRebornItems;
import limonblaze.blazereborn.util.BlazeRebornConfig;
import limonblaze.blazereborn.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public class SoulBrewingStandBlockEntity extends BrewingStandBlockEntity {

    public SoulBrewingStandBlockEntity(BlockPos pWorldPosition, BlockState pState) {
        super(pWorldPosition, pState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlazeRebornBlockEntityTypes.SOUL_BREWING_STAND_ENTITY.get();
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent(Utils.createTranslation("container", "soul_brewing_stand"));
    }

    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new SoulBrewingStandMenu(pId, pPlayer, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SoulBrewingStandBlockEntity blockEntity) {
        ItemStack itemstack = blockEntity.items.get(4);
        if (blockEntity.fuel <= 0 && itemstack.is(BlazeRebornItems.SOUL_BLAZE_POWDER.get())) {
            blockEntity.fuel = 20;
            itemstack.shrink(1);
            setChanged(level, pos, state);
        }

        boolean flag = isBrewable(blockEntity.items);
        boolean flag1 = blockEntity.brewTime > 0;
        ItemStack itemstack1 = blockEntity.items.get(3);
        if (flag1) {
            --blockEntity.brewTime;
            boolean flag2 = blockEntity.brewTime == 0;
            if (flag2 && flag) {
                doBrew(level, pos, blockEntity.items);
                setChanged(level, pos, state);
            } else if (!flag || !itemstack1.is(blockEntity.ingredient)) {
                blockEntity.brewTime = 0;
                setChanged(level, pos, state);
            }
        } else if (flag && blockEntity.fuel > 0) {
            --blockEntity.fuel;
            blockEntity.brewTime = BlazeRebornConfig.SERVER.brewing.soulBrewingStandBrewTime.get();
            blockEntity.ingredient = itemstack1.getItem();
            setChanged(level, pos, state);
        }

        boolean[] aboolean = blockEntity.getPotionBits();
        if (!Arrays.equals(aboolean, blockEntity.lastPotionCount)) {
            blockEntity.lastPotionCount = aboolean;
            BlockState blockstate = state;
            if (!(state.getBlock() instanceof BrewingStandBlock)) {
                return;
            }

            for(int i = 0; i < BrewingStandBlock.HAS_BOTTLE.length; ++i) {
                blockstate = blockstate.setValue(BrewingStandBlock.HAS_BOTTLE[i], aboolean[i]);
            }

            level.setBlock(pos, blockstate, 2);
        }

    }

}