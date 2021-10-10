package tests;

import helpers.Specs;
import models.ApiResponse;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {

    @Test
    @Order(1)
    public void createUser() {
        JSONObject requestParams = new JSONObject();
        requestParams
                .put("id", "1")
                .put("username", "art")
                .put("firstName", "art")
                .put("lastName", "naz")
                .put("email", "art@naz.com")
                .put("password", "12345")
                .put("phone", "54321")
                .put("userStatus", "1");

        ApiResponse response =
                given()
                        .spec(Specs.request)
                        .header("Content-Type", "application/json").
                        when()
                        .body(requestParams.toString())
                        .post("/user").
                        then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        assertEquals(200, response.getCode());
        assertEquals("unknown", response.getType());
        assertEquals("1", response.getMessage());
    }

    @Test
    @Order(2)
    public void createUserWithList() {
        JSONObject requestParams = new JSONObject();
        requestParams
                .put("id", "1")
                .put("username", "art")
                .put("firstName", "art")
                .put("lastName", "naz")
                .put("email", "art@naz.com")
                .put("password", "12345")
                .put("phone", "54321")
                .put("userStatus", "1");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(0, requestParams);

        ApiResponse response =
                given()
                        .spec(Specs.request)
                        .header("Content-Type", "application/json").
                        when()
                        .body(jsonArray.toString())
                        .post("/user/createWithList").
                        then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        assertEquals(200, response.getCode());
        assertEquals("unknown", response.getType());
        assertEquals("ok", response.getMessage());
    }

    @Test
    @Order(6)
    public void getUserInfo() {
        User user =
                given()
                        .spec(Specs.request).
                        when()
                        .get("/user/art").
                        then()
                        .spec(Specs.response200)
                        .extract().as(User.class);

        assertEquals(1, user.getId());
        assertEquals("art", user.getUsername());
        assertEquals("ORI", user.getFirstName());
        assertEquals("naz", user.getLastName());
        assertEquals("art@naz.com", user.getEmail());
        assertEquals("12345", user.getPassword());
        assertEquals("54321", user.getPhone());
        assertEquals(1, user.getUserStatus());
    }

    @Test
    @Order(3)
    public void updateUser() {
        JSONObject requestParams = new JSONObject();
        requestParams
                .put("id", "1")
                .put("username", "art")
                .put("firstName", "ORI")
                .put("lastName", "naz")
                .put("email", "art@naz.com")
                .put("password", "12345")
                .put("phone", "54321")
                .put("userStatus", "1");

        ApiResponse response =
                given()
                        .spec(Specs.request)
                        .header("Content-Type", "application/json").
                        when()
                        .body(requestParams.toString())
                        .put("/user/art").
                        then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        assertEquals(200, response.getCode());
        assertEquals("unknown", response.getType());
        assertEquals("1", response.getMessage());

    }

    @Test
    @Order(4)
    public void userLogin() {
        ApiResponse response =
                given()
                        .param("username", "ar")
                        .param("password", "123")
                        .spec(Specs.request).
                        when()
                        .get("/user/login").
                        then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        assertEquals(200, response.getCode());
        assertEquals("unknown", response.getType());
        assertThat(response.getMessage()).contains("logged in user session:");
    }

    @Test
    @Order(5)
    public void UserLogout() {
        ApiResponse response =
                given()
                        .spec(Specs.request).
                        when()
                        .get("/user/logout").
                        then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        assertEquals(200, response.getCode());
        assertEquals("unknown", response.getType());
        assertEquals("ok", response.getMessage());
    }

    @Test
    @Order(7)
    public void deleteUser() {
        ApiResponse response =
        given()
                .spec(Specs.request).
                when()
                .delete("/user/art").
                then()
                .spec(Specs.response200)
                .extract().as(ApiResponse.class);

        assertEquals(200, response.getCode());
        assertEquals("unknown", response.getType());
        assertEquals("art", response.getMessage());
    }
}
