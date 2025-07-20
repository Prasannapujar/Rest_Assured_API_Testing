package org.restassured.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.example.Address;
import org.example.Geo;
import org.example.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AssignmentPojo {
    @BeforeClass
    public void setup() {

        RequestSpecBuilder builder= new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com")
                .addHeader("Content-Type", "application/json").
                log(io.restassured.filter.log.LogDetail.ALL);

        ResponseSpecBuilder responseSpecBuilder= new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType("application/json")
                .log(io.restassured.filter.log.LogDetail.ALL);


        io.restassured.RestAssured.requestSpecification = builder.build();
        io.restassured.RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void testPojo() {

        Geo geo = new Geo("-37.3159","81.1496");
        Address address = new Address("Vishnu Nagar", "Mumbai", "Maharashtra", "400610", geo);
        User user= new User("praveen", "prav", "praveen@gmail.com", address);

       User expectedUser= given()
                .body(user)
                .when()
                .post("/users")
                .then()
                .extract()
                .as(User.class);

       assertThat(expectedUser.getName(), equalTo("praveen"));
       assertThat(expectedUser.getId(), equalTo(11));
    }
}
