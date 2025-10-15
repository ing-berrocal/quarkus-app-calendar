/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.model.Appointment;
import com.calendario.app.model.AppointmentView;
import com.calendario.app.model.request.AppointmentRequest;
import com.calendario.app.service.AppointmentManager;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ITPB-RH
 */
@Path("/appointment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentAPI {
    
    @Inject
    private AppointmentManager appointmentManager;
    
    @GET
    public List<AppointmentView> getAppointments( 
        @QueryParam("queryDate")
        LocalDate queryDate,
        @QueryParam("queryService")
        Optional<Long> queryService) {
        return appointmentManager.getAppointment(queryDate,queryService);
    }

    @GET
    @Path("/schedule")
    public List<AppointmentView> getCalendarAppointments( 
        @QueryParam("queryDate")
        LocalDate queryDate) {
        
            LocalDate initDate = queryDate.plusDays(-15);
            LocalDate endDate = queryDate.plusDays(15);
            
        return appointmentManager.getRangeAppointment(initDate,endDate);
    }

    @POST
    @Path("/add")
    public Response addAppointment(@Valid AppointmentRequest appointmentRequest) {

        Appointment appointment = new Appointment(0L, 
        appointmentRequest.getRequestDate(), 
        appointmentRequest.getRequestTime(), 
        appointmentRequest.getServiceInstanceId(),
        appointmentRequest.getCustomerId()); 

        appointmentManager.createAppointment(appointment);

        return Response.ok().build();
    }

    @POST
    @Path("/add-with-time-range")
    public Response addAppointmentWithTimeRange(@Valid AppointmentRequest appointmentRequest) {

        Appointment appointment = new Appointment(0L, 
        appointmentRequest.getRequestDate(), 
        appointmentRequest.getRequestTime(), 
        appointmentRequest.getServiceInstanceId(),
        appointmentRequest.getCustomerId()); 

        appointmentManager.createAppointment(appointment);

        return Response.ok().build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response editAppointment(@Valid AppointmentRequest appointmentRequest) {

        Appointment appointment = new Appointment(0L, 
        appointmentRequest.getRequestDate(), 
        appointmentRequest.getRequestTime(), 
        appointmentRequest.getServiceInstanceId(),
        0L); 

        appointmentManager.createAppointment(appointment);

        return Response.ok().build();
    }
}
