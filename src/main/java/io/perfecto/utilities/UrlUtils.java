package io.perfecto.utilities;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {
    private static String perfectoServerAddress = "https://%s.perfectomobile.com/nexperience/perfectomobile/wd/hub/";
    private static String localServerAddress = "http://127.0.0.1:4723/wd/hub/";

    public static URL getUrl(String cloudName) throws MalformedURLException {

        boolean isLocal = cloudName.equals("local");
        String urlString = isLocal ? localServerAddress : String.format(perfectoServerAddress, cloudName);
        return new URL(urlString);
    }
}

