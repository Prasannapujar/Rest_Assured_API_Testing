package org.restassured.test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.config.LogConfig;
import static io.restassured.RestAssured.config;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class LogingHeaders extends BaseApiTest {

    @Test
    public void test()
    {
        System.out.println("Hello world");
    }
    
    @Test
    public void logSpecific() {
        given()
            .spec(requestSpecification)
            .log().headers()
        .when()
            .get("/workspaces")
        .then()
            .log().body()
            .statusCode(200)
            .body("workspaces.name", hasItems("Team Workspace", "My Workspace2"))
            .body("workspaces.size()", equalTo(2))
            .body("workspaces.name", hasItem("Team Workspace"));
    }

    @Test
    public void logWhenFailed() {
        given()
                .spec(requestSpecification)
                .log().headers()
                .when()
                .get("/workspaces")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("workspaces.name", hasItems("Team Workspace", "My Workspace"))
                .body("workspaces.size()", equalTo(2))
                .body("workspaces.name", hasItem("Team Workspace"));
    }

    @Test
    public void blacklistheader() {
        given()
                .spec(requestSpecification)
                .config(config().logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key")))
                .log().headers()
                .when()
                .get("/workspaces")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("workspaces.name", hasItems("Team Workspace", "My Workspace"))
                .body("workspaces.size()", equalTo(2))
                .body("workspaces.name", hasItem("Team Workspace"));
    }

    @Test
    public void logOnlyWhenErrored() {
        given()
                .spec(requestSpecification)
                .config(config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .when()
                .get("/workspaces")
                .then()
                .statusCode(200)
                .body("workspaces.name", hasItems("Team Workspace", "My Workspace"))
                .body("workspaces.size()", equalTo(2))
                .body("workspaces.name", hasItem("Team Workspace"));
    }


    }


