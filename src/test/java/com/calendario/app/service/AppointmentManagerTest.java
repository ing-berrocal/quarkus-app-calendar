/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario.app.service;

import com.calendario.app.exception.AppointmentException;
import com.calendario.app.model.Appointment;
import com.calendario.persistence.appointment.AppointmentRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 *
 * @author ITPB-RH
 */
@QuarkusComponentTest
public class AppointmentManagerTest {
    
    @Inject
    AppointmentManager appointmentManager;
    
    @InjectMock
    AppointmentRepository appointmentRepository;
    
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
     * Test of createAppointment method, of class AppointmentManager.
     */
   // @Test
    public void addAppointment() {

        Mockito.when(
                appointmentRepository.isAvailable(
                        ArgumentMatchers.any(LocalDate.class),
                        ArgumentMatchers.any(LocalTime.class),
                        ArgumentMatchers.anyLong())
        ).thenReturn(Boolean.TRUE);
        
        Mockito.when(appointmentRepository.addAppointment(ArgumentMatchers.any(Appointment.class)))
                .thenReturn(new Appointment(null,LocalDate.now(),LocalTime.now(),1L,1L));
        
        Appointment appointment = new Appointment(null,LocalDate.now(),LocalTime.now(),1L,1L);
        
        Assertions.assertDoesNotThrow(()->{        
            appointmentManager.createAppointment(appointment);

        });
    }
    
    //@Test
    public void existAppointmentInDateAndTime() {

        Mockito.when(
                appointmentRepository.isAvailable(
                        ArgumentMatchers.any(LocalDate.class),
                        ArgumentMatchers.any(LocalTime.class),
                        ArgumentMatchers.anyLong())
        ).thenReturn(Boolean.FALSE);
        
        Mockito.when(appointmentRepository.addAppointment(ArgumentMatchers.any(Appointment.class)))
                .thenReturn(new Appointment(null,LocalDate.now(),LocalTime.now(),1L,1L));
        
        Appointment appointment = new Appointment(null,LocalDate.now(),LocalTime.now(),1L,1L);
        
        AppointmentException assertThrows = Assertions.assertThrows(AppointmentException.class, ()->{
            appointmentManager.createAppointment(appointment);
        });
        
        // TODO review the generated test code and remove the default call to fail.
        
        Assertions.assertEquals("Appointment is not available",assertThrows.getMessage());
    }
    
}
