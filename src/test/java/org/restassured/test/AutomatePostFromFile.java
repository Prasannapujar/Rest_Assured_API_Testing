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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePostFromFile {

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
    public void createworkspacefile() {
        File requestFile = new File("src/main/resources/createworkspace.json");
        RestAssured.given()
                .body(requestFile)
                .when()
                .post("/workspaces")
                .then()
                .assertThat()
                .body("workspace.name", equalTo("Frontend Dev Hub"));
    }
   @Test
    public void createworkspaceFromHashmap() {
        Map<String, Object> workspaceDetails = new HashMap<>();
        workspaceDetails.put("name", "Frontend Dev Hub");
        workspaceDetails.put("type", "personal");
        workspaceDetails.put("description", "A workspace for frontend development projects");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("workspace", workspaceDetails);
        RestAssured.given()
                .body(requestBody)
                .when()
                .post("/workspaces")
                .then()
                .assertThat()
                .body("workspace.name", equalTo("Frontend Dev Hub"));
    }


}
