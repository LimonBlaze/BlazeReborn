package dev.limonblaze.blazereborn.api.extension.fire;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Implemented in {@link Entity} on the client side to handle the render of flame
 **/
@OnlyIn(Dist.CLIENT)
public interface FireVariantRenderedEntity {

    FireVariant getRenderedFireVariant();

}
