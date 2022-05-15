package limonblaze.blazereborn.util;

import com.google.gson.JsonSyntaxException;
import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.StringJoiner;

public class TextUtils {

    public static String createTranslation(String category, String... suffixs) {
        StringJoiner joiner = new StringJoiner(".", category, "").add(BlazeRebornAPI.MODID);
        for(String suffix : suffixs) {
            joiner.add(suffix);
        }
        return joiner.toString();
    }

    public static <T extends IForgeRegistryEntry<T>> String registryName(T entry) {
        ResourceLocation id = entry.getRegistryName();
        if(id == null) throw new JsonSyntaxException("Required registry name for " + entry.getRegistryType() + " but found none.");
        return id.toString();
    }

}
