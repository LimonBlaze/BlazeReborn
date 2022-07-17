package limonblaze.blazereborn.util;

import limonblaze.blazereborn.api.BlazeRebornAPI;

import java.util.StringJoiner;

public class MiscUtils {

    public static String createTranslation(String category, String... suffixs) {
        StringJoiner joiner = new StringJoiner(".").add(category).add(BlazeRebornAPI.MODID);
        for(String suffix : suffixs) {
            joiner.add(suffix);
        }
        return joiner.toString();
    }

}
