package org.restassured.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class DefaultRequestSpec {
    String apiKey;


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

        RestAssured.requestSpecification = requestSpecBuilder.build();



    }

    @Test
    public void testGetWorkspaces() {
        given()
                .get("/workspaces")
                .then()
                .statusCode(200)
                .body("workspaces.name", hasItems("Team Workspace", "My Workspace"));
    }

    @Test
    public void queryRequestSpec()
    {
        QueryableRequestSpecification queryableRequestSpecification= SpecificationQuerier.query(RestAssured.requestSpecification);
        System.out.println("Base URI: " + queryableRequestSpecification.getBaseUri());
    }

}
