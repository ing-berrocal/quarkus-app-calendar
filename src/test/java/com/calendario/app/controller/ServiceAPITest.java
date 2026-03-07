/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.model.Service;
import com.calendario.app.model.request.ServiceRequest;
import com.calendario.app.service.ServiceManager;
import com.calendario.domain.model.ServiceInstance;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ServiceAPITest {

    @InjectMock
    //@Mock
    ServiceManager serviceManager;

    RequestSpecification rq = given().basePath("/api");

    @org.junit.jupiter.api.BeforeAll
    static void beforeAll() {
        // Runs once before all tests. Add global setup here if needed.
        /*
        ServiceManager mock = Mockito.mock(ServiceManager.class);
        Mockito.when(mock.greet("Stuart")).thenReturn("A mock for Stuart");
        QuarkusMock.installMockForType(mock, ServiceManager.class);  
        */
    }

    @Test
    void testGetAllServices() {

        var service = new Service(1L, "Test", "Desc", 30, 1);
        Mockito.when(serviceManager.findAll()).thenReturn(List.of(service));

        given().basePath("/api")
        .when().get("/service")
        .then()
            .statusCode(200)
            .body("$", hasSize(1))
            .body("[0].title", equalTo("Test"));
    }

    @Test
    void testGetServicesByInstance() {

        Long serviceId = 1L;
        var instance = new ServiceInstance(serviceId,"Employee1",Boolean.TRUE);
        Mockito.when(serviceManager.findByServiceId(serviceId)).thenReturn(List.of(instance));

        given().basePath("/api")
        .queryParam("service-id", serviceId)
        .when().get("/service/instance")
        .then()
            .statusCode(200)
            .body("$", hasSize(1))
            .body("[0].employeeName", equalTo("Employee1"))
            ;
    }

    @Test
    void testGetServicesById_found() {
        
        var service = new Service(1L, "Test", "Desc", 30, 1);
        Mockito.when(serviceManager.findById(1L)).thenReturn(service);

        given().basePath("/api")
        .when().pathParam("id", 1)
        .get("/service/by/{id}")
        .then()
            .statusCode(200)
            .body("serviceId", equalTo(1))
            .body("title", equalTo("Test"));
    }

    @Test
    void testGetServicesById_notFound() {
        Mockito.when(serviceManager.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Not Found"));

        given().basePath("/api")
        .when().get("/service/by/99")
        .then()
            .statusCode(404); // O ajusta según el comportamiento real
    }

    @Test
    void testAddService_success() {

        ServiceRequest req = new ServiceRequest();
        req.setTitle("New Service");
        req.setDescription("Desc");
        req.setDurationInMinute(30);
        req.setServiceConcurrency(1);

        Mockito.when(
            serviceManager.persist(
                Mockito.any(Service.class)
                )
            )
        .thenReturn(new Service(2L, "New Service", "Desc", 30, 1));

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/service")
        .then()
            .statusCode(200);
    }

    @Test
    void testAddService_invalidRequest() {

        ServiceRequest req = new ServiceRequest(); // Faltan campos requeridos

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().post("/service")
        .then()
            .statusCode(400);
    }

    @Test
    void testEditServices_notImplemented() {

        ServiceRequest req = new ServiceRequest();
        req.setTitle("Edit");
        req.setDescription("EditDesc");
        req.setDurationInMinute(20);
        req.setServiceConcurrency(2);

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when()
        .pathParam("id", 1)
        .put("/service/edit/{id}")
        .then()
            .statusCode(200); // O ajusta según el comportamiento real
    }

    @Test
    void testEditStatus_notImplemented() {
        ServiceRequest req = new ServiceRequest();
        req.setTitle("Edit");
        req.setDescription("EditDesc");
        req.setDurationInMinute(20);
        req.setServiceConcurrency(2);

        given().basePath("/api")
        .contentType(ContentType.JSON)
        .body(req)
        .when().put("/service/status/1")
        .then()
            .statusCode(200 ); // O ajusta según el comportamiento real
    }
}
