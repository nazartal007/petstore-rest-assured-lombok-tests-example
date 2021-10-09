package tests;

import helpers.Specs;
import models.ApiResponse;
import models.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreTests {

    @Test
    @org.junit.jupiter.api.Order(1)
    public void postOrder() {
        JSONObject requestParams = new JSONObject();
        requestParams
                .put("id", "100")
                .put("petId", "1")
                .put("quantity", "1")
                .put("shipDate", "2021-10-05T02:13:36.877Z")
                .put("status", "placed")
                .put("complete", "true");

        Order order =
                given()
                        .spec(Specs.request)
                        .header("Content-Type", "application/json").
                when()
                        .body(requestParams.toString())
                        .post("/store/order").
                then()
                        .spec(Specs.response200)
                        .extract().as(Order.class);

        assertEquals(100, order.getId());
        assertEquals(1, order.getPetId());
        assertEquals(1, order.getQuantity());
        assertEquals("2021-10-05T02:13:36.877+0000", order.getShipDate());
        assertEquals("placed", order.getStatus());
        assertEquals(true, order.getComplete());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void getOrder() {
        Order order =
                given()
                        .spec(Specs.request).
                when()
                        .get("/store/order/100").
                then()
                        .spec(Specs.response200)
                        .extract().as(Order.class);

        assertEquals(100, order.getId());
        assertEquals(1, order.getPetId());
        assertEquals(1, order.getQuantity());
        assertEquals("2021-10-05T02:13:36.877+0000", order.getShipDate());
        assertEquals("placed", order.getStatus());
        assertEquals(true, order.getComplete());

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void deleteOrder() {
        ApiResponse apiResponse =
                given()
                        .spec(Specs.request).
                when()
                        .delete("/store/order/100").
                then()
                        .spec(Specs.response200)
                        .extract().as(ApiResponse.class);

        assertEquals(200, apiResponse.getCode());
        assertEquals("unknown", apiResponse.getType());
        assertEquals("100", apiResponse.getMessage());
    }

    @Test
    public void deleteOrder2() { //todo иногда возвращает 200, что странно
        ApiResponse apiResponse =
                given()
                        .spec(Specs.request).
                when()
                        .delete("/store/order/100").
                then()
                        .spec(Specs.response404)
                        .extract().as(ApiResponse.class);

        assertEquals(404, apiResponse.getCode());
        assertEquals("unknown", apiResponse.getType());
        assertEquals("Order Not Found", apiResponse.getMessage());
    }

    @Test
    public void getInventory() {
        given()
                .spec(Specs.request).
        when()
                .get("/store/inventory").
        then()
                .spec(Specs.response200); //todo надо подумать как ответ проверить ещё

    }

}
