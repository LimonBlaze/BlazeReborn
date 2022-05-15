package limonblaze.blazereborn.util;

import limonblaze.blazereborn.api.BlazeRebornAPI;

import java.util.StringJoiner;

public class TextUtils {

    public static String createTranslation(String category, String... suffixs) {
        StringJoiner joiner = new StringJoiner(".", category, "").add(BlazeRebornAPI.MODID);
        for(String suffix : suffixs) {
            joiner.add(suffix);
        }
        return joiner.toString();
    }

}
