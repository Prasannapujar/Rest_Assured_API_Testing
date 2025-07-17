package org.restassured.test;



import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.with;

public class nonBDD {
    String apiKey;
    RequestSpecification requestSpecification;


    @BeforeClass
    public void setup() throws IOException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("setup.properties")) {
            if (input == null) {
                throw new IOException("Unable to find setup.properties");
            }
            props.load(input);
            apiKey = props.getProperty("api.key");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load setup.properties", e);
        }

         requestSpecification = with()
                .baseUri("https://api.getpostman.com")
                .header("X-Api-Key", apiKey);
    }

    @Test
    public void testGetWorkspaces() {
        requestSpecification
            .get("/workspaces")
            .then()
            .statusCode(200)
            .log().all();
    }
}