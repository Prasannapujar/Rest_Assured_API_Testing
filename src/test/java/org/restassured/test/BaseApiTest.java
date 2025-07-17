package org.restassured.test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BaseApiTest {
    protected static String key;
    protected  static  Header header;
    protected static Headers headers;
    protected static HashMap<String,String> headersMap = new HashMap<>();

    @BeforeClass
    public static void setUp() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("setup.properties")) {
            props.load(input);
            key = props.getProperty("api.key");
            header= new Header("X-Api-key",key);
            headers= new Headers(header);
            // Populate headersMap with the key-value pairs from the properties file

            headersMap.put("X-Api-key", key);
            headersMap.put("Content-Type", "application/json");

            if (key == null) {
                throw new RuntimeException("api.key not found in setup.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load setup.properties", e);
        }
    }
} 