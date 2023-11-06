package io.perfecto.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CommonProperties {

    private static Properties properties;

    public static String getProperty(String propertyName, Object defaultValue) {

        if (properties != null)
            return properties.getProperty(propertyName, (String)defaultValue);

        try (InputStream input = new FileInputStream(CommonProperties.class.getClassLoader().getResource("common.properties").getPath())) {
            properties = new Properties();
            properties.load(input);
            return properties.getProperty(propertyName);

        } catch (Exception ex) {
            ex.printStackTrace();
            return (String)defaultValue;
        }
    }

    public static String getProperty(String propertyName) {

        if (properties != null)
            return properties.getProperty(propertyName, null);

        try (InputStream input = new FileInputStream(CommonProperties.class.getClassLoader().getResource("common.properties").getPath())) {
            properties = new Properties();
            properties.load(input);
            return properties.getProperty(propertyName);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
