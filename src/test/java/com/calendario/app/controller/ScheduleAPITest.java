/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.service.ScheduleManager;
import model.Tuple;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ScheduleAPITest {

    @InjectMock
    ScheduleManager scheduleManager;

    @org.junit.jupiter.api.BeforeAll
    static void beforeAll() {
        // Runs once before all tests. Add global setup here if needed.
    }

    @Test
    void testGetSchedule_success() {
        // Arrange
        LocalDate queryDate = LocalDate.of(2025, 10, 16);
        Long serviceId = 1L;
        
        Tuple[] expectedSchedule = {
            new Tuple(480, 30, 1), // 08:00, 30 minutes, 1 slot available
            new Tuple(510, 30, 1), // 08:30, 30 minutes, 1 slot available
            new Tuple(540, 30, 1)  // 09:00, 30 minutes, 1 slot available
        };
        
        Mockito.when(scheduleManager.getScheduleAvailable(queryDate, serviceId))
               .thenReturn(expectedSchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", queryDate.toString())
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(3))
            .body("[0].time", equalTo(480))
            .body("[0].durationInMinutes", equalTo(30))
            .body("[0].count", equalTo(1))
            .body("[1].time", equalTo(510))
            .body("[2].time", equalTo(540));
    }

    @Test
    void testGetSchedule_withoutQueryDate() {
        // Arrange
        Long serviceId = 1L;
        Tuple[] emptySchedule = {};
        
        Mockito.when(scheduleManager.getScheduleAvailable(Mockito.any(), Mockito.eq(serviceId)))
               .thenReturn(emptySchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(0));
    }

    @Test
    void testGetSchedule_withoutServiceId() {
        // Arrange
        LocalDate queryDate = LocalDate.of(2025, 10, 16);
        Tuple[] emptySchedule = {};
        
        Mockito.when(scheduleManager.getScheduleAvailable(Mockito.eq(queryDate), Mockito.any()))
               .thenReturn(emptySchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", queryDate.toString())
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(0));
    }

    @Test
    void testGetSchedule_emptyResult() {
        // Arrange
        LocalDate queryDate = LocalDate.of(2025, 12, 25); // Christmas day - no available slots
        Long serviceId = 1L;
        Tuple[] emptySchedule = {};
        
        Mockito.when(scheduleManager.getScheduleAvailable(queryDate, serviceId))
               .thenReturn(emptySchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", queryDate.toString())
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(0));
    }

    @Test
    void testGetSchedule_multipleTimeSlots() {
        // Arrange
        LocalDate queryDate = LocalDate.of(2025, 10, 17);
        Long serviceId = 2L;
        
        Tuple[] fullDaySchedule = {
            new Tuple(480, 60, 2), // 08:00, 60 minutes, 2 slots available
            new Tuple(540, 60, 1), // 09:00, 60 minutes, 1 slot available
            new Tuple(600, 60, 2), // 10:00, 60 minutes, 2 slots available
            new Tuple(660, 60, 0), // 11:00, 60 minutes, 0 slots available (fully booked)
            new Tuple(720, 60, 1)  // 12:00, 60 minutes, 1 slot available
        };
        
        Mockito.when(scheduleManager.getScheduleAvailable(queryDate, serviceId))
               .thenReturn(fullDaySchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", queryDate.toString())
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(5))
            .body("[0].time", equalTo(480))
            .body("[0].count", equalTo(2))
            .body("[1].count", equalTo(1))
            .body("[2].count", equalTo(2))
            .body("[3].count", equalTo(0))
            .body("[4].count", equalTo(1));
    }

    @Test
    void testGetSchedule_serviceManagerThrowsException() {
        // Arrange
        LocalDate queryDate = LocalDate.of(2025, 10, 16);
        Long serviceId = 999L; // Non-existent service
        
        Mockito.when(scheduleManager.getScheduleAvailable(queryDate, serviceId))
               .thenThrow(new RuntimeException("Service not found"));

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", queryDate.toString())
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(500); // Or adjust based on actual error handling behavior
    }

    @Test
    void testGetSchedule_pastDate() {
        // Arrange
        LocalDate pastDate = LocalDate.of(2025, 1, 1);
        Long serviceId = 1L;
        Tuple[] emptySchedule = {};
        
        Mockito.when(scheduleManager.getScheduleAvailable(pastDate, serviceId))
               .thenReturn(emptySchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", pastDate.toString())
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(0));
    }

    @Test
    void testGetSchedule_futureDate() {
        // Arrange
        LocalDate futureDate = LocalDate.of(2026, 12, 31);
        Long serviceId = 1L;
        
        Tuple[] futureSchedule = {
            new Tuple(540, 45, 1), // 09:00, 45 minutes, 1 slot available
            new Tuple(585, 45, 1)  // 09:45, 45 minutes, 1 slot available
        };
        
        Mockito.when(scheduleManager.getScheduleAvailable(futureDate, serviceId))
               .thenReturn(futureSchedule);

        // Act & Assert
        given().basePath("/api")
        .queryParam("queryDate", futureDate.toString())
        .queryParam("serviceId", serviceId)
        .when().get("/schedule")
        .then()
            .statusCode(200)
            .body("$", hasSize(2))
            .body("[0].time", equalTo(540))
            .body("[0].durationInMinutes", equalTo(45))
            .body("[1].time", equalTo(585));
    }
}