/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.model.Service;
import com.calendario.app.model.request.ServiceRequest;
import com.calendario.app.service.ServiceManager;
import com.calendario.domain.model.ServiceInstance;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
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
import jakarta.ws.rs.core.Response.ResponseBuilder;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ITPB-RH
 */
@Authenticated
@Path("/service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ServiceAPI {

    private static final Logger logger = LoggerFactory.getLogger(ServiceAPI.class);

    @Inject
    JsonWebToken jwt;

    private final ServiceManager serviceManager;

    public ServiceAPI(ServiceManager serviceManager, JsonWebToken jwt) {
        this.serviceManager = serviceManager;
        this.jwt = jwt;
    }

    @GET
    // @RolesAllowed({ "User", "Admin" })
    public List<Service> getAllServices() {
        logger.info("Fetching all services: {}", jwt.getSubject());
        return serviceManager.findAll();
    }

    @GET
    @Path("/instance")
    public List<ServiceInstance> getServices(@QueryParam("service-id") Long serviceId) {
        return serviceManager.findByServiceId(serviceId);
    }

    @GET
    @Path("/by/{id}")
    public Response getServicesById(@PathParam("id") Long serviceId) {
        try {
            Service byId = serviceManager.findById(serviceId);
            ResponseBuilder ok = Response.ok(byId);
            return ok.build();
        } catch (Exception e) {
            // Handle exception
            logger.info("Error fetching service by ID: {}", serviceId, e);
            System.out.println("Error fetching service by ID: " + serviceId + " - " + e.getMessage());
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addService(@Valid ServiceRequest serviceRequest) {

        var serviceParam = new Service(null,
                serviceRequest.getTitle(),
                serviceRequest.getDescription(),
                serviceRequest.getDurationInMinute(),
                serviceRequest.getServiceConcurrency());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Error adding service: {}", e);
        }

        serviceManager.persist(serviceParam);

        return Response.ok().build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response editServices(@PathParam("id") Long id,
            @Valid ServiceRequest serviceRequest) {
                
        var serviceParam = new Service(id,
                serviceRequest.getTitle(),
                serviceRequest.getDescription(),
                serviceRequest.getDurationInMinute(),
                serviceRequest.getServiceConcurrency());


        serviceManager.editService(serviceParam);

        return Response.ok().build();
    }

    @PUT
    @Path("/status/{id}")
    public Response editStatus(@PathParam("id") Long id,
            @Valid ServiceRequest serviceRequest) {
        return Response.ok().build();
    }
}
