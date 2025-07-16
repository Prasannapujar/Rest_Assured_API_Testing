package org.restassured.test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HelloRestAssured extends BaseApiTest {



    @Test
    public void test()
    {
        System.out.println("Hello world");
    }
    
    @Test
    public void testPostmanWorkspacesAPI() {
        given()
            .log().all()
            .baseUri("https://api.getpostman.com")
            .header("X-Api-Key", key)
        .when()
            .get("/workspaces")
        .then()
            .log().all()
            .statusCode(200)
            .body("workspaces.name", hasItems("Team Workspace", "My Workspace2"))
            .body("workspaces.size()", equalTo(2))
            .body("workspaces.name", hasItem("Team Workspace"));
    }


    @Test
    public void extractResponse() {
        Response response = given()
            .log().all()
            .baseUri("https://api.getpostman.com")
            .header("X-Api-Key", key)
        .when()
            .get("/workspaces")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();

        System.out.println(response.asString());
    }


    @Test
    public void extractJsonValues() {
        Response response = given()
            .log().all()
            .baseUri("https://api.getpostman.com")
            .header("X-Api-Key", key)
        .when()
            .get("/workspaces")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();

        // Option 1: Using JsonPath constructor
        JsonPath jp = new JsonPath(response.asString());
        System.out.println("workspace ===" + jp.getString("workspaces[0].name"));

        // Option 2: Using response.path()
        System.out.println("workspace ===" + response.path("workspaces[0].name"));

        // Option 3: Using JsonPath.from()
        System.out.println("workspace ===" + JsonPath.from(response.asString()).getString("workspaces[0].name"));
    }

    @Test
    public void different_validation() {
        given()
            .log().all()
            .baseUri("https://api.getpostman.com")
            .header("X-Api-Key", key)
        .when()
            .get("/workspaces")
        .then()
            .log().all()
            .statusCode(200)
            .body("workspaces.name", contains("Team Workspace", "My Workspace"))
            .body("workspaces.name", containsInAnyOrder("My Workspace","Team Workspace"),
                    "workspaces.name", is(not(empty())),
                    "workspaces.name", hasSize(2),
                    "workspaces[0]", hasKey("visibility"),
                    "workspaces[0]", hasValue("7922446"),
                    "workspaces[0]", hasEntry("type","personal")
            );

    }
}

