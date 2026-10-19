package com.calendario.app.controller;

import com.calendario.app.model.Employee;
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
    void testListEmployees_success() {
        Mockito.when(employeeManager.listEmployees())
                .thenReturn(java.util.List.of(                new Employee(1L, "John Doe"), new Employee(2L, "Jane Roe")));

        given().basePath("/api")
        .when().get("/employee")
        .then()
            .statusCode(200)
            .body("size()", equalTo(2))
                            .body("id", hasItems(1, 2))
            .body("fullName", hasItems("John Doe", "Jane Roe"));
    }

    @Test
    void testListEmployees_empty() {
        Mockito.when(employeeManager.listEmployees()).thenReturn(java.util.List.of());

        given().basePath("/api")
        .when().get("/employee")
        .then()
            .statusCode(200)
            .body("size()", equalTo(0));
    }

    @Test
    void testUpdateEmployeeName_success() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName("New Name");

        Mockito.when(employeeManager.updateEmployeeName(5L, "New Name")).thenReturn(true);

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().put("/employee/5")
        .then()
            .statusCode(200)
            .body("id", equalTo(5))
            .body("fullName", equalTo("New Name"));
    }

    @Test
    void testUpdateEmployeeName_notFound() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName("New Name");

        Mockito.when(employeeManager.updateEmployeeName(Mockito.anyLong(), Mockito.anyString())).thenReturn(false);

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().put("/employee/99")
        .then()
            .statusCode(404);
    }

    @Test
    void testUpdateEmployeeName_invalidName_returnsValidationError() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName("ab");

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().put("/employee/5")
        .then()
            .statusCode(400)
            .body("errors.field", hasItem("fullName"));
    }

    @Test
    void testAddEmployee_success() {
        EmployeeRequest req = new EmployeeRequest();
        req.setFullName("John Doe");

        Mockito.when(employeeManager.createEmployee(Mockito.any())).thenReturn(7L);

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/employee")
        .then()
            .statusCode(201)
            .body("id", equalTo(7))
            .body("fullName", equalTo("John Doe"));

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
