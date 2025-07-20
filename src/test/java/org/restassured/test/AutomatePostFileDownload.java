package org.restassured.test;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class AutomatePostFileDownload {




    @Test
    public void fileUpload() throws FileNotFoundException {
        File file = new File("Test.txt");
      byte[] bytes=  given().baseUri("https://raw.githubusercontent.com/")
             //   .contentType(ContentType.MULTIPART_FORM_DATA)

                .log().all()
                .when()
                .get("Prasannapujar/Rest_Assured_API_Testing/refs/heads/master/src/main/resources/createworkspace.json")
                .then().log().all()
                .extract().response().asByteArray();

      File file1=new File("src/main/resources/createworkspace2.json");
        OutputStream outputStream = new FileOutputStream(file1);
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }




}
