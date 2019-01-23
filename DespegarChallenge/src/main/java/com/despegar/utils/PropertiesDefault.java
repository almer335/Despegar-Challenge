package com.despegar.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesDefault {

    private Properties properties;

    public PropertiesDefault() {
        this.properties = new Properties();
    }

    public Properties getProperties(){
        try {
            InputStream is = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/properties/default.properties");
            this.properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.properties;
    }

}
