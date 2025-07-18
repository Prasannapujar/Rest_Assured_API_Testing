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

public class AutomateDelete {

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
    public void deleteWorkspaceNonBdd() {
        String workspaceId = "7e7f6b71-4cae-489b-a4f8-651c672a04d6"; // Replace with the actual workspace ID you want to update


       Response response= with()
                .pathParam("workspaceId", workspaceId)
                .delete("/workspaces"+"/{workspaceId}");

        System.out.println(response.asString());

       // assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("workspace.id"), equalTo(workspaceId));
       // assertThat(response.jsonPath().getString("workspace.type"), equalTo("personal"));


    }
}
