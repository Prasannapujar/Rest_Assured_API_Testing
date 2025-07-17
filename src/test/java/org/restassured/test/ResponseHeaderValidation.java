package org.restassured.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseHeaderValidation extends  BaseApiTest{

    @Test
    public void testResponseHeaders() {
        given()
            .spec(requestSpecification)
            .log().all()
        .when()
            .get("/workspaces")
        .then()
            .log().all()
            .statusCode(200)
            // Option 1: .headers(
            //     "Content-Type", "application/json; charset=utf-8",
            //     "X-RateLimit-Limit", "10",
            //     "X-RateLimit-Remaining", "59"
            // )
            .header("Content-Type", "application/json; charset=utf-8")
            .header("X-RateLimit-Limit", "10")
            .header("X-RateLimit-Remaining", "59");
    }

    // add the test method to extract the response headers and it's values
    @Test
    public void extractResponseHeaders() {
        given()
            .spec(requestSpecification)
            .log().all()
        .when()
            .get("/workspaces")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .headers()
            .forEach(header -> System.out.println(header.getName() + ": " + header.getValue()));
    }
}
