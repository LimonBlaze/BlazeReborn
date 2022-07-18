package dev.limonblaze.blazereborn.mixin;

import dev.limonblaze.blazereborn.api.extension.blaze.SmallFireballCreatingBlaze;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Blaze.BlazeAttackGoal.class)
public class BlazeAttackGoalMixin {

    @Shadow
    @Final
    private Blaze blaze;

    @ModifyVariable(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private SmallFireball blazereborn$getFireballFromBlaze(SmallFireball smallFireball) {
        return ((SmallFireballCreatingBlaze) this.blaze).createSmallFireball(smallFireball);
    }

}
