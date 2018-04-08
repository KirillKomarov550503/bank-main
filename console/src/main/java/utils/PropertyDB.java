package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyDB {
    private Properties properties;
    private String filename;


    public PropertyDB() {
        this.filename = "console\\src\\main\\resources\\path.properties";
    }

    public String getProperty(String propertyName) {
        String propertyValue = null;
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream fileInputStream = new FileInputStream(filename);
                properties.load(fileInputStream);
                propertyValue = properties.getProperty(propertyName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return propertyValue;
    }
}

