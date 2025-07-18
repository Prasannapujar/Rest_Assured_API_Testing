package org.restassured.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
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
    ResponseSpecification responseSpecification;


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
//        responseSpecification=RestAssured.expect().statusCode(200).log().all()
//                .contentType(ContentType.JSON)
//                .body("workspaces.name", hasItems("Team Workspace", "My Workspace"));

        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200)
                .expectContentType(ContentType.XML)
                . log(LogDetail.ALL);

        responseSpecification=responseSpecBuilder.build();
        RestAssured.responseSpecification=responseSpecBuilder.build();


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

    @Test
    public void testGetWorkspaces2() {  // with response specification
        given()
                .get("/workspaces")
                .then().spec(responseSpecification);

    }

    @Test
    public void testGetWorkspaces_default() {
        given()
                .get("/workspaces");

    }


}
