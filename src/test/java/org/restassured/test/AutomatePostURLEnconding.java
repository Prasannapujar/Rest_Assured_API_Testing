package org.restassured.test;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RestAssuredConfig.config;

public class AutomatePostURLEnconding {




    @Test
    public void urlEncoding()   {

        given().baseUri("https://postman-echo.com")
                .config(config().encoderConfig(EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("key 1", "value 1")
                .formParam("key2", "value2")
                .log().all()
                .when()
                .post("/post")
                .then().log().all()
                .statusCode(200);

        }
}
