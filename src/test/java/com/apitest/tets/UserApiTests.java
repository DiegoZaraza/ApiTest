package com.apitest.tets;


import com.apitest.base.BaseTest;
import com.apitest.models.User;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Pet Store API")
@Feature("User Management")
public class UserApiTests extends BaseTest {

    private static final String TEST_USERNAME = "testuser123";
    private static final String UPDATED_FIRST_NAME = "Updated";
    private static final String TEST_PASSWORD = "testpass123";

    @Test(priority = 1)
    @Story("Create User")
    @Description("Test creating a new user")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {
        User user = new User();
        user.setId(12345L);
        user.setUsername(TEST_USERNAME);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPassword(TEST_PASSWORD);
        user.setPhone("123-456-7890");
        user.setUserStatus(1);

        getRequestSpecification()
                .body(user)
                .when()
                .post("/user")
                .then()
                .statusCode(200)
                .body("message", notNullValue());
    }

    @Test(priority = 2)
    @Story("User Login")
    @Description("Test user login functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testUserLogin() {
        getRequestSpecification()
                .queryParam("username", TEST_USERNAME)
                .queryParam("password", TEST_PASSWORD)
                .when()
                .get("/user/login")
                .then()
                .statusCode(200)
                .body("message", containsString("logged in user session"));
    }

    @Test(priority = 3)
    @Story("User Logout")
    @Description("Test user logout functionality")
    @Severity(SeverityLevel.NORMAL)
    public void testUserLogout() {
        getRequestSpecification()
                .when()
                .get("/user/logout")
                .then()
                .statusCode(200)
                .body("message", equalTo("ok"));
    }

    @Test
    @Story("Create Users with Array")
    @Description("Test creating multiple users with array input")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateUsersWithArray() {
        User user1 = new User(1L, "arrayuser1", "Array", "User1",
                "array1@example.com", "pass123", "111-111-1111", 1);
        User user2 = new User(2L, "arrayuser2", "Array", "User2",
                "array2@example.com", "pass456", "222-222-2222", 1);

        List<User> users = Arrays.asList(user1, user2);

        getRequestSpecification()
                .body(users)
                .when()
                .post("/user/createWithArray")
                .then()
                .statusCode(200)
                .body("message", notNullValue());
    }

    @Test
    @Story("Create Users with List")
    @Description("Test creating multiple users with list input")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateUsersWithList() {
        User user1 = new User(3L, "listuser1", "List", "User1",
                "list1@example.com", "pass789", "333-333-3333", 1);
        User user2 = new User(4L, "listuser2", "List", "User2",
                "list2@example.com", "pass012", "444-444-4444", 1);

        List<User> users = Arrays.asList(user1, user2);

        getRequestSpecification()
                .body(users)
                .when()
                .post("/user/createWithList")
                .then()
                .statusCode(200)
                .body("message", notNullValue());
    }

    @Test
    @Story("Get Invalid User")
    @Description("Test retrieving a user that doesn't exist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetInvalidUser() {
        getRequestSpecification()
                .pathParam("username", "nonexistentuser")
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(404)
                .body("message", equalTo("User not found"));
    }

    @Test(priority = 4)
    @Story("Delete User")
    @Description("Test deleting a user")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteUser() {
        getRequestSpecification()
                .pathParam("username", TEST_USERNAME)
                .when()
                .delete("/user/{username}")
                .then()
                .statusCode(200);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Verify the user is deleted
        getRequestSpecification()
                .pathParam("username", TEST_USERNAME)
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(404);
    }
}