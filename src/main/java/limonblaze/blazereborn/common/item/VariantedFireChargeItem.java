package limonblaze.blazereborn.common.item;

import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.api.extension.fire.FireVariantSourceItem;
import net.minecraft.Util;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.Random;
import java.util.function.Supplier;

public class VariantedFireChargeItem extends FireChargeItem implements FireVariantSourceItem {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR;
    private final Supplier<FireVariant> fireVariant;

    public VariantedFireChargeItem(Supplier<FireVariant> fireVariant, Properties properties) {
        super(properties);
        this.fireVariant = fireVariant;
    }

    @Override
    public FireVariant getFireVariant(Entity entity, ItemStack stack) {
        return this.fireVariant.get();
    }

    static {
        DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {

            public ItemStack execute(BlockSource blsrc, ItemStack stack) {
                Direction direction = blsrc.getBlockState().getValue(DispenserBlock.FACING);
                Position position = DispenserBlock.getDispensePosition(blsrc);
                double d0 = position.x() + direction.getStepX() * 0.3F;
                double d1 = position.y() + direction.getStepY() * 0.3F;
                double d2 = position.z() + direction.getStepZ() * 0.3F;
                Level level = blsrc.getLevel();
                Random random = level.random;
                double d3 = random.nextGaussian() * 0.05D + direction.getStepX();
                double d4 = random.nextGaussian() * 0.05D + direction.getStepY();
                double d5 = random.nextGaussian() * 0.05D + direction.getStepZ();
                SmallFireball fireball = new SmallFireball(level, d0, d1, d2, d3, d4, d5);
                level.addFreshEntity(Util.make(fireball, pFireball -> pFireball.setItem(stack)));
                stack.shrink(1);
                return stack;
            }

            protected void playSound(BlockSource blsrc) {
                blsrc.getLevel().levelEvent(1018, blsrc.getPos(), 0);
            }

        };
    }

}
