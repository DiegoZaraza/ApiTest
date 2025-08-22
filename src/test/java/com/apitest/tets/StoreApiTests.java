package com.apitest.tets;

import com.apitest.base.BaseTest;
import com.apitest.models.Order;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Pet Store API")
@Feature("Store Management")
public class StoreApiTests extends BaseTest {

    private static final Long TEST_ORDER_ID = 98765L;
    private static final Long TEST_PET_ID = 12345L;

    @Test(priority = 1)
    @Story("Place Pet Order")
    @Description("Test placing an order for a pet")
    @Severity(SeverityLevel.CRITICAL)
    public void testPlaceOrder() {
        Order order = new Order();
        order.setId(TEST_ORDER_ID);
        order.setPetId(TEST_PET_ID);
        order.setQuantity(1);
        order.setShipDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()));
        order.setStatus("placed");
        order.setComplete(true);

        getRequestSpecification()
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200)
                .body("id", equalTo(TEST_ORDER_ID.intValue()))
                .body("petId", equalTo(TEST_PET_ID.intValue()))
                .body("quantity", equalTo(1))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
    }

    @Test(priority = 2, dependsOnMethods = "testPlaceOrder")
    @Story("Get Order by ID")
    @Description("Test retrieving an order by its ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetOrderById() {
        getRequestSpecification()
                .pathParam("orderId", TEST_ORDER_ID)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(TEST_ORDER_ID.intValue()))
                .body("petId", equalTo(TEST_PET_ID.intValue()))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
    }

    @Test(priority = 3)
    @Story("Get Store Inventory")
    @Description("Test getting pet inventories by status")
    @Severity(SeverityLevel.NORMAL)
    public void testGetInventory() {
        getRequestSpecification()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test(priority = 4)
    @Story("Get Order with Invalid ID")
    @Description("Test retrieving an order with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetOrderByInvalidId() {
        getRequestSpecification()
                .pathParam("orderId", "invalid")
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(404)
                .body("message", containsString("input string"));
    }

    @Test(priority = 5)
    @Story("Get Non-existent Order")
    @Description("Test retrieving an order that doesn't exist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetNonExistentOrder() {
        getRequestSpecification()
                .pathParam("orderId", 999999999L)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Order not found"));
    }

    @Test(priority = 6)
    @Story("Place Invalid Order")
    @Description("Test placing an order with invalid data")
    @Severity(SeverityLevel.NORMAL)
    public void testPlaceInvalidOrder() {
        String invalidOrderJson = "{\"id\": \"invalid\", \"petId\": \"invalid\"}";

        getRequestSpecification()
                .body(invalidOrderJson)
                .when()
                .post("/store/order")
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(500)));
    }

    @Test(priority = 7)
    @Story("Place Order with Missing Data")
    @Description("Test placing an order with missing required data")
    @Severity(SeverityLevel.NORMAL)
    public void testPlaceOrderWithMissingData() {
        Order incompleteOrder = new Order();
        incompleteOrder.setId(55555L);
        // Missing petId, quantity, etc.

        getRequestSpecification()
                .body(incompleteOrder)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200); // API is lenient with missing data
    }

    @Test(priority = 8)
    @Story("Place Order with Negative Quantity")
    @Description("Test placing an order with negative quantity")
    @Severity(SeverityLevel.NORMAL)
    public void testPlaceOrderWithNegativeQuantity() {
        Order order = new Order();
        order.setId(77777L);
        order.setPetId(TEST_PET_ID);
        order.setQuantity(-1);
        order.setStatus("placed");
        order.setComplete(false);

        getRequestSpecification()
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200) // API accepts negative quantity
                .body("quantity", equalTo(-1));
    }

    @Test(priority = 9)
    @Story("Verify Inventory Structure")
    @Description("Test that inventory returns proper data structure")
    @Severity(SeverityLevel.MINOR)
    public void testInventoryStructure() {
        getRequestSpecification()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200)
                .body("$", instanceOf(java.util.Map.class));
    }

    @Test(priority = 10, dependsOnMethods = "testGetOrderById")
    @Story("Delete Order")
    @Description("Test deleting an order")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteOrder() {
        getRequestSpecification()
                .pathParam("orderId", TEST_ORDER_ID)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .statusCode(200);

        // Verify the order is deleted
        getRequestSpecification()
                .pathParam("orderId", TEST_ORDER_ID)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(404);
    }
}