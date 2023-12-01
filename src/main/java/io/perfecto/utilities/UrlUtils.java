package io.perfecto.utilities;

public class UrlUtils {
    private static String perfectoServerAddress = "https://%s.perfectomobile.com/nexperience/perfectomobile/wd/hub/";
    private static String localServerAddress = "http://127.0.0.1:4723/wd/hub/";

    public static String buildUrl(String cloudName) {

        boolean isLocal = cloudName.equals("local");
        if (isLocal)
            return localServerAddress;

        return cloudName.startsWith("http") ? cloudName
        : String.format(perfectoServerAddress, cloudName);
    }
}

