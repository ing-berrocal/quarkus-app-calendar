/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario.app.controller;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.calendario.app.model.Customer;
import com.calendario.app.model.request.CustomerRequest;
import com.calendario.app.service.CustomerManager;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@QuarkusTest
class CustomerAPITest {

    @InjectMock
    CustomerManager customerManager;

    RequestSpecification rq = given().basePath("/api");

    @org.junit.jupiter.api.BeforeAll
    static void beforeAll() {
        // Runs once before all tests. Add global setup here if needed.
    }

    @Test
    void testGetCustomers() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFullname("Juan Pérez");
        customer1.setDocumentNumber("12345678");
        customer1.setTelephone("123456789");
        customer1.setEmail("juan@example.com");
        customer1.setState(true);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFullname("María González");
        customer2.setDocumentNumber("87654321");
        customer2.setTelephone("987654321");
        customer2.setEmail("maria@example.com");
        customer2.setState(true);

        Mockito.when(customerManager.getCustomers()).thenReturn(List.of(customer1, customer2));

        // Act & Assert
        given().basePath("/api")
        .when().get("/customer")
        .then()
            .statusCode(200)
            .body("$", hasSize(2))
            .body("[0].fullname", equalTo("Juan Pérez"))
            .body("[0].documentNumber", equalTo("12345678"))
            .body("[1].fullname", equalTo("María González"))
            .body("[1].documentNumber", equalTo("87654321"));
    }

    @Test
    void testGetCustomers_emptyList() {
        // Arrange
        Mockito.when(customerManager.getCustomers()).thenReturn(List.of());

        // Act & Assert
        given().basePath("/api")
        .when().get("/customer")
        .then()
            .statusCode(200)
            .body("$", hasSize(0));
    }

    @Test
    void testGetCustomerByDocument_found() {
        
        // Arrange
        String documentNumber = "12345678";

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullname("Juan Pérez");
        customer.setDocumentNumber(documentNumber);
        customer.setTelephone("123456789");
        customer.setEmail("juan@example.com");
        customer.setState(true);

        Mockito.when(customerManager.getCustomerByDocumentNumber(documentNumber))
               .thenReturn(Optional.of(customer));

        // Use CustomerRequest as JSON body
        CustomerRequest req = new CustomerRequest();
        req.setDocumentNumber(documentNumber);

        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/customer/by-document")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("fullname", equalTo("Juan Pérez"))
            .body("documentNumber", equalTo(documentNumber))
            .body("email", equalTo("juan@example.com"));
    }

    /*@Test
    void testGetCustomerByDocument_notFound() {
        // Arrange
        String documentNumber = "99999999";
        Mockito.when(customerManager.getCustomerByDocumentNumber(documentNumber))
               .thenReturn(Optional.empty());

        // Act & Assert
        given().basePath("/api")
        .when().pathParam("documentNumber", documentNumber)
        .get("/customer/by-document")
        .then()
            .statusCode(404);
    }*/

    @Test
    void testAddCustomer_success() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setFullName("Carlos Rodríguez");
        request.setDocumentNumber("11223344");
        request.setTelephone("555666777");
        request.setAddress("Calle 123");
        request.setEmail("carlos@example.com");

        // Mock that customer doesn't exist
        Mockito.when(customerManager.getCustomerByDocumentNumber("11223344"))
               .thenReturn(Optional.empty());

        Mockito.doAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.setId(42L);
            return null;
        }).when(customerManager).addCustomer(Mockito.any(Customer.class));

        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(request)
        .when().post("/customer")
        .then()
            .statusCode(201)
            .body("id", equalTo(42))
            .body("fullname", equalTo("Carlos Rodríguez"))
            .body("documentNumber", equalTo("11223344"))
            .body("telephone", equalTo("555666777"))
            .body("address", equalTo("Calle 123"))
            .body("email", equalTo("carlos@example.com"))
            .body("state", equalTo(true));
    }

    @Test
    void testAddCustomer_alreadyExists() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setFullName("Carlos Rodríguez");
        request.setDocumentNumber("11223344");
        request.setTelephone("555666777");
        request.setAddress("Calle 123");
        request.setEmail("carlos@example.com");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setDocumentNumber("11223344");

        // Mock that customer already exists
        Mockito.when(customerManager.getCustomerByDocumentNumber("11223344"))
               .thenReturn(Optional.of(existingCustomer));

        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(request)
        .when().post("/customer")
        .then()
            .statusCode(500); // Or adjust based on actual error handling behavior
    }

    /*@Test
    void testAddCustomer_invalidRequest_nullBody() {
        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body((String) null)
        .when().post("/customer")
        .then()
            .statusCode(500);
    }*/

    @Test
    void testAddCustomer_invalidRequest_emptyFields() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        // Leave all fields empty/null

        // Mock that customer doesn't exist
        Mockito.when(customerManager.getCustomerByDocumentNumber(Mockito.any()))
               .thenReturn(Optional.empty());

        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(request)
        .when().post("/customer")
        .then()
            .statusCode(400);
    }

    @Test
    void testAddCustomer_serviceThrowsException() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setFullName("Test User");
        request.setDocumentNumber("12345678");
        request.setTelephone("555666777");
        request.setEmail("test@example.com");

        // Mock that customer doesn't exist initially
        Mockito.when(customerManager.getCustomerByDocumentNumber("12345678"))
               .thenReturn(Optional.empty());

        // Mock addCustomer to throw exception (e.g., database error)
        Mockito.doThrow(new RuntimeException("Database error"))
               .when(customerManager).addCustomer(Mockito.any(Customer.class));

        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(request)
        .when().post("/customer")
        .then()
            .statusCode(500); // Or adjust based on actual error handling behavior
    }

    /*@Test
    void testGetCustomerByDocument_specialCharacters() {
        // Arrange
        String documentNumber = "A1B2C3D4";
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullname("Usuario Especial");
        customer.setDocumentNumber(documentNumber);

        Mockito.when(customerManager.getCustomerByDocumentNumber(documentNumber))
               .thenReturn(Optional.of(customer));

        // Act & Assert
        given().basePath("/api")
        .when().pathParam("documentNumber", documentNumber)
        .get("/customer/by-document")
        .then()
            .statusCode(200)
            .body("documentNumber", equalTo(documentNumber))
            .body("fullname", equalTo("Usuario Especial"));
    }*/

    @Test
    void testAddCustomer_withAllFields() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setId(999L); // Should be ignored in creation
        request.setFullName("Ana Martínez");
        request.setDocumentNumber("55667788");
        request.setTelephone("111222333");
        request.setAddress("Avenida Principal 456");
        request.setEmail("ana@example.com");

        // Mock that customer doesn't exist
        Mockito.when(customerManager.getCustomerByDocumentNumber("55667788"))
               .thenReturn(Optional.empty());

        Mockito.doNothing().when(customerManager).addCustomer(Mockito.any(Customer.class));

        // Act & Assert
        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(request)
        .when().post("/customer")
        .then()
            .statusCode(201)
            .body("fullname", equalTo("Ana Martínez"))
            .body("documentNumber", equalTo("55667788"))
            .body("telephone", equalTo("111222333"))
            .body("address", equalTo("Avenida Principal 456"))
            .body("email", equalTo("ana@example.com"))
            .body("state", equalTo(true));
    }
}