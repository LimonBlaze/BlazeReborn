package dev.limonblaze.blazereborn.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SoulMagmaBlock extends MagmaBlock {
    
    public SoulMagmaBlock(Properties properties) {
        super(properties);
    }
    
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity entity) {
        if (!entity.fireImmune() && entity instanceof LivingEntity living && !EnchantmentHelper.hasSoulSpeed(living)) {
            entity.hurt(DamageSource.HOT_FLOOR, 2.0F);
        }
    }
    
}
