package org.restassured.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseHeaderValidation extends  BaseApiTest{

    @Test
    public void testResponseHeaders() {
        given()
            .log().all()
            .baseUri("https://api.getpostman.com")
                .headers(headersMap) // This line is commented out as per the edit hint
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
}
