package org.restassured.test;

import org.testng.annotations.BeforeClass;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BaseApiTest {
    protected static String key;

    @BeforeClass
    public static void setUp() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("setup.properties")) {
            props.load(input);
            key = props.getProperty("api.key");
            if (key == null) {
                throw new RuntimeException("api.key not found in setup.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load setup.properties", e);
        }
    }
} 