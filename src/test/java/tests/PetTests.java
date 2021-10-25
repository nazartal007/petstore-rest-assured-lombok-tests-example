package tests;

import helpers.Specs;
import models.ApiResponse;
import models.Pet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;


public class PetTests {

    @Test
    public void addNewPet() {
        JSONObject requestParams = new JSONObject();
        requestParams
                .put("id", "1")
                .put("category", new JSONObject())
                .put("name", "ORI")
                .put("photoUrls", new String[3])
                .put("tags", new JSONArray())
                .put("status", "available");

        Pet response =
                given()
                        .spec(Specs.request)
                        .header("Content-Type", "application/json").
                        when()
                        .body(requestParams.toString())
                        .post("/pet").
                        then()
                        .spec(Specs.response200)
                        .extract().as(Pet.class);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals("ORI", response.getName());
        Assertions.assertEquals("available", response.getStatus());
        Assertions.assertEquals("available", response.getStatus());
    }

    @Test
    public void petUploadImage() {
        ApiResponse response =
                given()
                        .spec(Specs.request)
                        .header("accept","application/json")
                        .header("Content-Type", "multipart/form-data").
                        when()
                        .multiPart("file", new File("C:\\Users\\art\\IdeaProjects\\petstore-rest-assured-tests-example\\src\\test\\resources\\photo_2021-09-26_00-09-01.jpg"))
                        .multiPart("additionalMetadata", "I dont know")
                        .post("/pet/1/uploadImage").
                        then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        Assertions.assertEquals(200, response.getCode());
        Assertions.assertEquals("unknown", response.getType());
        Assertions.assertEquals("additionalMetadata: I dont know\nFile uploaded to ./photo_2021-09-26_00-09-01.jpg, 51892 bytes", response.getMessage());
    }

    @Test
    public void updatePet() {
        JSONObject requestParams = new JSONObject();
        requestParams
                .put("id", "1")
                .put("category", new JSONObject())
                .put("name", "ORI")
                .put("photoUrls", new String[3])
                .put("tags", new JSONArray())
                .put("status", "available");

        Pet response =
                given()
                        .spec(Specs.request)
                        .header("Content-Type", "application/json").
                        when()
                        .body(requestParams.toString())
                        .put("/pet").
                        then()
                        .spec(Specs.response200)
                        .extract().as(Pet.class);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals("ORI", response.getName());
        Assertions.assertEquals("available", response.getStatus());
        Assertions.assertEquals("available", response.getStatus());
    }

}
