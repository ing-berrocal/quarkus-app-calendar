/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.controller;

import com.calendario.app.model.Service;
import com.calendario.app.model.request.ServiceRequest;
import com.calendario.app.model.request.ServiceStatusRequest;
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

import java.net.URI;
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
            return Response.ok(byId).build();
        } catch (Exception e) {
            logger.info("Error fetching service by ID: {}", serviceId, e);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addService(@Valid ServiceRequest serviceRequest) {

        var serviceParam = new Service(null,
                serviceRequest.getCode(),
                serviceRequest.getTitle(),
                serviceRequest.getDescription(),
                serviceRequest.getDurationInMinute(),
                serviceRequest.getServiceConcurrency(),
                Boolean.TRUE);

        var created = serviceManager.persist(serviceParam);

        if (created != null && created.serviceId() != null) {
            return Response.created(URI.create("/service/by/" + created.serviceId())).entity(created).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response editServices(@PathParam("id") Long id,
            @Valid ServiceRequest serviceRequest) {
                
        var serviceParam = new Service(id,
                serviceRequest.getCode(),
                serviceRequest.getTitle(),
                serviceRequest.getDescription(),
                serviceRequest.getDurationInMinute(),
                serviceRequest.getServiceConcurrency(),
                Boolean.TRUE);


        serviceManager.editService(serviceParam);

        return Response.ok().build();
    }

    @PUT
    @Path("/status/{id}")
    public Response editStatus(@PathParam("id") Long id,
            @Valid ServiceStatusRequest statusRequest) {
        try {
            serviceManager.changeState(id, statusRequest.getState());
        } catch (RuntimeException e) {
            logger.info("Error changing service status: {}", id, e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
}
