package dev.limonblaze.blazereborn.common.effect;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SoulPiercingEffect extends MobEffect {
    
    public SoulPiercingEffect() {
        super(MobEffectCategory.HARMFUL, 0x00CCFF);
        this.addAttributeModifier(Attributes.ARMOR, "b67af2e8-c918-4a5a-96f9-e0442d52aa9d", -0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    
    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return Mth.clamp(super.getAttributeModifierValue(pAmplifier, pModifier), -1.0, 0.0);
    }
    
}
