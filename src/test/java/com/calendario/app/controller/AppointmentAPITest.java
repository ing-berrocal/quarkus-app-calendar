/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.model.Appointment;
import com.calendario.app.model.AppointmentView;
import com.calendario.app.model.request.AppointmentRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ITPB-RH
 */
@QuarkusTest
@TestHTTPEndpoint(AppointmentAPI.class)
public class AppointmentAPITest {
    
    public AppointmentAPITest() {
    }
    
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getCalendar method, of class AppointmentAPI.
     */
    @Test
    public void testGetCalendar() {
        System.out.println("getCalendar");
        AppointmentAPI instance = new AppointmentAPI();
        List<Appointment> expResult = null;
        List<AppointmentView> result = instance.getCalendarAppointments(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAppointment method, of class AppointmentAPI.
     */
    @Test
    public void testAddAppointment() {
        
        JsonObject json = new JsonObject("""
                                         {
                                             "requestDate":"2025-03-01",
                                             "requestTime":"14:00",
                                             "serviceInstanceId":1
                                         }
                                         """);
        given()
          .when()
                //.pathParams(map)
                //.queryParam(string, clctn)
                .body(json.toString())
                .post("/add")
          .then()
             .statusCode(200);
        
        
    }
    
}
