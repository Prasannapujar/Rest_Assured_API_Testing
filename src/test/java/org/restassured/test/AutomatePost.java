package org.restassured.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePost {

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
                .expectContentType(ContentType.JSON)
                . log(LogDetail.ALL);

      //  responseSpecification=responseSpecBuilder.build();
        RestAssured.responseSpecification=responseSpecBuilder.build();


    }

    @Test
    public void createworkspace() {
        String requestBody = "{\n" +
                "  \"workspace\": {\n" +
                "    \"name\": \"New Workspace2\",\n" +
                "    \"type\": \"personal\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .body(requestBody)
                .when()
                .post("/workspaces")
                .then()
                .assertThat()
                .body("workspace.name", equalTo("New Workspace2"));
    }

    @Test
    public void createworkspace_nonBDD() {
        String requestBody = "{\n" +
                "  \"workspace\": {\n" +
                "    \"name\": \"New Workspace3\",\n" +
                "    \"type\": \"personal\"\n" +
                "  }\n" +
                "}";


       Response response= with()
                .body(requestBody)
                .post("/workspaces");

        System.out.println(response.asString());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("workspace.name"), equalTo("New Workspace3"));


    }
}
