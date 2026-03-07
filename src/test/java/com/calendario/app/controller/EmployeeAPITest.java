package com.calendario.app.controller;

import com.calendario.app.model.request.EmployeeRequest;
import com.calendario.app.service.EmployeeManager;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class EmployeeAPITest {

    @InjectMock
    EmployeeManager employeeManager;

    @Test
    void testAddEmployee_success() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName("John Doe");

        Mockito.doNothing().when(employeeManager).createEmployee(Mockito.any());

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/employee")
        .then()
            .statusCode(201);

        Mockito.verify(employeeManager).createEmployee(Mockito.any());
    }

    @Test
    void testAddEmployee_invalidRequest_blankName_returnsValidationError() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName(""); // Blank name violates @NotBlank

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/employee")
        .then()
            .statusCode(400)
            .body("message", equalTo("Validation failed"))
            .body("errors", not(empty()))
            .body("errors.field", hasItem("fullName"));
    }

    @Test
    void testAddEmployee_invalidRequest_tooShortName_returnsValidationError() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName("ab"); // Violates @Size(min = 3)

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/employee")
        .then()
            .statusCode(400)
            .body("message", equalTo("Validation failed"))
            .body("errors", not(empty()))
            .body("errors.field", hasItem("fullName"));
    }
}
