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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePostComplexBody {

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
                .setBaseUri("https://e049fca2-be38-4f76-aec9-5518fd64bca8.mock.pstmn.io")
                .addHeader("x-mock-match-request-body", "true")
                .addHeader("Content-Type", "application/json;charset=utf-8")
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

       List<Integer> rgba= Arrays.asList(255, 255, 255, 1);
        HashMap<String,Object> code= new HashMap<>();
        code.put("rgba", rgba);
        code.put("hex", "#000");

        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("color","black");
        map1.put("category", "hue");
        map1.put("type", "primary");
        map1.put("code", code);

        List<Integer> rgba2= Arrays.asList(0, 0, 0, 1);
        HashMap<String,Object> code2= new HashMap<>();
        code2.put("rgba", rgba2);
        code2.put("hex", "#FFF");
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("color","white");
        map2.put("category", "value");
        map2.put("code", code2);

        List<HashMap<String, Object>> colors = Arrays.asList(map1, map2);
        HashMap<String,List<HashMap<String,Object>>> requestBody = new HashMap<>();
        requestBody.put("colors", colors);


       Response response= RestAssured.given()
                .body(requestBody)
                .when()
                .post("/postcomplexJson")
                .then().extract().response();
        assertThat(response.statusCode(), equalTo(200));

    }


}
