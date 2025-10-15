/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.model.Service;
import com.calendario.app.model.request.ServiceRequest;
import com.calendario.app.service.ServiceManager;
import com.calendario.domain.model.ServiceInstance;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.List;

/**
 *
 * @author ITPB-RH
 */
@Path("/service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceAPI {
    
    private final ServiceManager serviceManager;
    
    public ServiceAPI(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @GET
    public List<Service> getAllServices() {
        return serviceManager.findAll();
    }

    @GET
    @Path("/instance")
    public List<ServiceInstance> getServices(@QueryParam("service-id") Long serviceId) {
        return serviceManager.findByServiceId(serviceId);
    }

    @GET
    @Path("/by/{id}")
    public Service getServicesById(@PathParam("id") Long serviceId) {
        return serviceManager.findById(serviceId);
    }

    @POST
    public Response addService(@Valid ServiceRequest serviceRequest) {

        var serviceParam = new Service(null,
            serviceRequest.getTitle(),
            serviceRequest.getDescription(), 
            serviceRequest.getDurationInMinute(), 
            serviceRequest.getServiceConcurrency());

        serviceManager.persist(serviceParam);

        return Response.ok().build();
    }

    @PUT
    @Path("/edit/{id}")
    public Service editServices(@PathParam("id") Long id,
    @Valid ServiceRequest serviceRequest) {
        return null;
    }

    @PUT
    @Path("/status/{id}")
    public Service editStatus(@PathParam("id") Long id,
    @Valid ServiceRequest serviceRequest) {
        return null;
    }
}
