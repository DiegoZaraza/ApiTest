package com.apitest.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static final String BASE_URL = "https://petstore.swagger.io/v2";
    protected static final String CONTENT_TYPE = "application/json";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000));
    }

    protected RequestSpecification getRequestSpecification() {
        return given()
                .filter(new AllureRestAssured())
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE);
    }

    protected RequestSpecification getRequestSpecificationWithoutContentType() {
        return given()
                .filter(new AllureRestAssured());
    }
}