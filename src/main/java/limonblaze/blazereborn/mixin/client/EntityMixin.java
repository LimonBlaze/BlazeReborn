package limonblaze.blazereborn.mixin.client;

import limonblaze.blazereborn.api.extension.FireVariant;
import limonblaze.blazereborn.api.extension.FireVariantRenderedEntity;
import limonblaze.blazereborn.api.extension.FireVariantHoldingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
@Mixin(Entity.class)
public class EntityMixin implements FireVariantRenderedEntity {

    @Override
    public @Nonnull
    FireVariant getRenderedFireVariant() {
        return ((FireVariantHoldingEntity)this).getFireVariant();
    }

}
