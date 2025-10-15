/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario.app.api;

import com.calendario.app.model.Service;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
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
public class ServiceAPITest {
    
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
     * Test of getAllServices method, of class ServiceAPI.
     */
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/services")
          .then()
             .statusCode(200);
    }
    
}
