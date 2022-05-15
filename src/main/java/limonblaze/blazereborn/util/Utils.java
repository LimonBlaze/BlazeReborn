package limonblaze.blazereborn.util;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class Utils {

    public static String createTranslation(String category, String... suffixs) {
        StringJoiner joiner = new StringJoiner(".", category, "").add(BlazeRebornAPI.MODID);
        for(String suffix : suffixs) {
            joiner.add(suffix);
        }
        return joiner.toString();
    }

    public static String compressResourceLocations(ResourceLocation... resourceLocations) {
        StringJoiner joiner = new StringJoiner("__with__");
        for(ResourceLocation resourceLocation : resourceLocations) {
            joiner.add(resourceLocation.getNamespace() + "__id__" + resourceLocation.getPath());
        }
        return joiner.toString();
    }

    public static List<ResourceLocation> decompressResourceLocations(String compressed) {
        String[] rawIds = compressed.split("__with__");
        List<ResourceLocation> result = new LinkedList<>();
        for(String rawId : rawIds) {
            try {
                String[] nameSpaceAndPath = rawId.split("__id__");
                result.add(new ResourceLocation(nameSpaceAndPath[0] + ":" + nameSpaceAndPath[1]));
            } catch(Exception exception) {
                BlazeReborn.LOGGER.error("Error in decompress resource loactions: \"" + compressed + "\"", exception);
            }
        }
        return result;
    }

}
