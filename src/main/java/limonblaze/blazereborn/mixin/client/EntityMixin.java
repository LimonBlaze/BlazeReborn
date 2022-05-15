package limonblaze.blazereborn.mixin.client;

import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.api.extension.fire.FireVariantRenderedEntity;
import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
@Mixin(Entity.class)
public class EntityMixin implements FireVariantRenderedEntity {

    @Override
    @Nonnull
    public FireVariant getRenderedFireVariant() {
        return ((FireVariantHoldingEntity)this).getFireVariant();
    }

}
