package limonblaze.blazereborn.util;

import com.google.gson.JsonSyntaxException;
import com.mojang.math.Vector3f;
import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.StringJoiner;

public class MiscUtils {

    public static String createTranslation(String category, String... suffixs) {
        StringJoiner joiner = new StringJoiner(".", category, "").add(BlazeRebornAPI.MODID);
        for(String suffix : suffixs) {
            joiner.add(suffix);
        }
        return joiner.toString();
    }

    public static <T extends IForgeRegistryEntry<T>> ResourceLocation registryName(T entry) {
        ResourceLocation id = entry.getRegistryName();
        if(id == null) throw new UnsupportedOperationException("Requested registry entry is unregistered!");
        return id;
    }

    public static Vec3i unwrapRGB(int color) {
        return new Vec3i(
            (color & 0xFF0000) >> 16,
            (color & 0x00FF00) >> 8,
            color & 0x0000FF
        );
    }


}
