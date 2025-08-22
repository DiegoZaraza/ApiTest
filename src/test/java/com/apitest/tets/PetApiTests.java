package com.apitest.tets;

import com.apitest.base.BaseTest;
import com.apitest.models.Pet;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

@Epic("Pet Store API")
@Feature("Pet Management")
public class PetApiTests extends BaseTest {

    private static final Long TEST_PET_ID = 12345678L;
    private static final String TEST_PET_NAME = "Rocket";
    private static final String UPDATED_PET_NAME = "Rocket Raccoon";

    @Test(priority = 1)
    @Story("Add New Pet")
    @Description("Test adding a new pet to the store")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddNewPet() {
        Pet pet = new Pet();
        pet.setId(TEST_PET_ID);
        pet.setName(TEST_PET_NAME);
        pet.setStatus("available");
        pet.setPhotoUrls(List.of("http://example.com/photo1.jpg"));

        getRequestSpecification()
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("id", equalTo(TEST_PET_ID.intValue()))
                .body("name", equalTo(TEST_PET_NAME))
                .body("status", equalTo("available"));
    }

    @Test(priority = 2, dependsOnMethods = "testAddNewPet")
    @Story("Get Pet by ID")
    @Description("Test retrieving a pet by its ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetPetById() {
        getRequestSpecification()
                .pathParam("petId", TEST_PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(TEST_PET_ID.intValue()))
                .body("name", equalTo(TEST_PET_NAME))
                .body("status", equalTo("available"));
    }

    @Test(priority = 3, dependsOnMethods = "testGetPetById")
    @Story("Update Pet")
    @Description("Test updating an existing pet")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdatePet() {
        Pet updatedPet = new Pet();
        updatedPet.setId(TEST_PET_ID);
        updatedPet.setName(UPDATED_PET_NAME);
        updatedPet.setStatus("sold");
        updatedPet.setPhotoUrls(List.of("http://example.com/photo2.jpg"));

        getRequestSpecification()
                .body(updatedPet)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("id", equalTo(TEST_PET_ID.intValue()))
                .body("name", equalTo(UPDATED_PET_NAME))
                .body("status", equalTo("sold"));
    }

    @Test(priority = 4)
    @Story("Find Pets by Status")
    @Description("Test finding pets by their status")
    @Severity(SeverityLevel.NORMAL)
    public void testFindPetsByStatus() {
        getRequestSpecification()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("[0].status", equalTo("available"));
    }

    @Test(priority = 5)
    @Story("Get Pet by Invalid ID")
    @Description("Test retrieving a pet with an invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetPetByInvalidId() {
        getRequestSpecification()
                .pathParam("petId", 12345933L)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Pet not found"));
    }

    @Test(priority = 6)
    @Story("Delete Pet")
    @Description("Test deleting a pet")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeletePet() {
        getRequestSpecificationWithoutContentType()
                .pathParam("petId", TEST_PET_ID)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Verify the pet is deleted
        getRequestSpecification()
                .pathParam("petId", TEST_PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(404);
    }

    @Test(priority = 7)
    @Story("Add Pet with Invalid Data")
    @Description("Test adding a pet with invalid data")
    @Severity(SeverityLevel.NORMAL)
    public void testAddPetWithInvalidData() {
        String invalidPetJson = "";

        getRequestSpecification()
                .body(invalidPetJson)
                .when()
                .post("/pet")
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(405), equalTo(500)));
    }

    @Test(priority = 8)
    @Story("Update Pet with Form Data")
    @Description("Test updating pet name and status using form data")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePetWithFormData() {
        // First create a pet
        Pet pet = new Pet();
        pet.setId(54321L);
        pet.setName("Form Test Pet");
        pet.setStatus("available");

        getRequestSpecification()
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        // Update using form data
        getRequestSpecificationWithoutContentType()
                .contentType("application/x-www-form-urlencoded")
                .pathParam("petId", 54321L)
                .formParam("name", "Updated Form Pet")
                .formParam("status", "pending")
                .when()
                .post("/pet/{petId}")
                .then()
                .statusCode(200);
    }
}