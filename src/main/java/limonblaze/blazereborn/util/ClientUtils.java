package limonblaze.blazereborn.util;

import limonblaze.blazereborn.api.extension.FireVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ClientUtils {
    public static final HashMap<FireVariant, TextureAtlasSprite> FIRE_SPRITES_0 = new HashMap<>();
    public static final HashMap<FireVariant, TextureAtlasSprite> FIRE_SPRITES_1 = new HashMap<>();

    public static TextureAtlasSprite getSpriteForFireVariant(FireVariant variant, boolean forScreenEffect) {
        return (forScreenEffect ? FIRE_SPRITES_1 : FIRE_SPRITES_0).computeIfAbsent(variant, pVariant -> {
            ResourceLocation id = pVariant.getId();
            return ForgeHooksClient.getBlockMaterial(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_" + (forScreenEffect ? "1" : "0"))).sprite();
        });
    }

}
