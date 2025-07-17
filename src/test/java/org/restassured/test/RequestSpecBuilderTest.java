package org.restassured.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RequestSpecBuilderTest {
    String apiKey;
    RequestSpecification requestSpecification;

    @BeforeClass
    public void setup() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("setup.properties")) {
            props.load(input);
            apiKey = props.getProperty("api.key");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load setup.properties", e);
        }

            RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                    .setBaseUri("https://api.getpostman.com")
                    .addHeader("X-Api-Key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .log(LogDetail.ALL);

            requestSpecification = requestSpecBuilder.build();



    }

        @Test
        public void testGetWorkspaces() {
            given(requestSpecification)
                .get("/workspaces")
                .then()
                .statusCode(200)
                .body("workspaces.name", hasItems("Team Workspace", "My Workspace"));
        }
}
