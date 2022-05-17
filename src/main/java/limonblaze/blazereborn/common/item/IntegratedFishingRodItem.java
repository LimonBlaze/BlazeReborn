package limonblaze.blazereborn.common.item;

import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.common.entity.projectile.IntegratedFishingHook;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.Supplier;

public class IntegratedFishingRodItem<T extends IntegratedFishingHook> extends FishingRodItem {
    private final HookFactory<T> hookFactory;
    private final int luck;
    private final int lureSpeed;
    private final int enchantmentValue;

    public IntegratedFishingRodItem(HookFactory<T> hookFactory, int luck, int lureSpeed, int enchantmentValue, Properties properties) {
        super(properties);
        this.hookFactory = hookFactory;
        this.luck = luck;
        this.lureSpeed = lureSpeed;
        this.enchantmentValue = enchantmentValue;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.fishing != null) {
            if (!level.isClientSide) {
                int i = player.fishing.retrieve(itemstack);
                itemstack.hurtAndBreak(i, player, pPlayer -> pPlayer.broadcastBreakEvent(hand));
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            level.gameEvent(player, GameEvent.FISHING_ROD_REEL_IN, player);
        } else {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                int luck = EnchantmentHelper.getFishingLuckBonus(itemstack) + this.luck;
                int lureSpeed = EnchantmentHelper.getFishingSpeedBonus(itemstack) + this.lureSpeed;
                level.addFreshEntity(this.hookFactory.createHook(player, level, luck, lureSpeed));
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            level.gameEvent(player, GameEvent.FISHING_ROD_CAST, player);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    public interface HookFactory<T extends IntegratedFishingHook> {

        T createHook(Player player, Level level, int luck, int lureSpeed);

    }

}
