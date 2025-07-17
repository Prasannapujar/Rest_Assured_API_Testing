package org.restassured.test;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpec extends  BaseApiTest {

    @Test
    public void testRequestSpecification() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .statusCode(200);
    }

}
