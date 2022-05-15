package limonblaze.blazereborn.api;

import net.minecraft.resources.ResourceLocation;

public class BlazeRebornAPI {

    public static final String MODID = "blaze_reborn";

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }

}
