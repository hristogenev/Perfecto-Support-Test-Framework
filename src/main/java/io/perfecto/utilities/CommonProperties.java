package io.perfecto.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CommonProperties {

    private static Properties properties;

    private static void loadProperties() {
        try (InputStream input = new FileInputStream(CommonProperties.class.getClassLoader().getResource("common.properties").getPath())) {
            properties = new Properties();
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object getProperty(String propertyName, String defaultValue) {

        if (properties == null)
            loadProperties();

        return properties.getProperty(propertyName, defaultValue);
    }

    public static String getProperty(String propertyName) {

        if (properties == null)
            loadProperties();

        return properties.getProperty(propertyName, null);
    }
}
