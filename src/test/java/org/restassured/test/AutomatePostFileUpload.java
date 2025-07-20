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
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePostFileUpload {




    @Test
    public void fileUpload() {
        File file = new File("Test.txt");
        given().baseUri("https://postman-echo.com")
             //   .contentType(ContentType.MULTIPART_FORM_DATA)
                .multiPart("file", file)
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200);

    }




}
