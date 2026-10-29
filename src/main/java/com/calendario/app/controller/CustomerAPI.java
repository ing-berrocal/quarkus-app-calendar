package com.calendario.app.controller;

import com.calendario.app.exception.AppointmentException;
import com.calendario.app.model.Customer;
import com.calendario.app.model.request.CustomerRequest;
import com.calendario.app.model.request.SearchRequest;
import com.calendario.app.service.CustomerManager;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;  
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Authenticated
@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerAPI {
    
    private final CustomerManager customerManager;

    public CustomerAPI(CustomerManager customerManager){
        this.customerManager = customerManager;
    }

    @GET
    public Response getCustomers() {
        return Response.ok(customerManager.getCustomers()).build();
    }

    @POST
    @Path("/by-document")
    public Response getCustomerByDocument(CustomerRequest customerRequest) {

        return customerManager.getCustomerByDocumentNumber(customerRequest.getDocumentNumber())
            .map(customer -> Response.ok(customer).build())
            .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build()
            );
    }

    @POST
    @Path("/search")
    public Response getSearched(SearchRequest customerRequest) {
        return Response.ok(customerManager.searchCustomer(customerRequest.getText())).build();
    }

    @POST
    public Response addCustomer(@Valid CustomerRequest customerRequest) {

        /*
        customerManager.getCustomerByDocumentNumber(customerRequest.getDocumentNumber())
            .ifPresent(customer -> {
                throw new AppointmentException("Customer already exists with document number: " + customerRequest.getDocumentNumber());
            });
            */
           
        Customer customer = new Customer();
        customer.setFullname(customerRequest.getFullName());
        customer.setDocumentNumber(customerRequest.getDocumentNumber());    
        customer.setTelephone(customerRequest.getTelephone());
        customer.setAddress(customerRequest.getAddress());
        customer.setEmail(customerRequest.getEmail());
        customer.setState(true);

        customerManager.addCustomer(customer);
        
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    public Response updateCustomer(@Valid CustomerRequest customerRequest) {

        if (customerRequest.getId() == null) {
            throw new BadRequestException("Customer id is required");
        }

           
        Customer customer = new Customer();
        customer.setFullname(customerRequest.getFullName());
        customer.setDocumentNumber(customerRequest.getDocumentNumber());    
        customer.setTelephone(customerRequest.getTelephone());
        customer.setAddress(customerRequest.getAddress());
        //customer.setState(true);
        customerManager.updateTercero(customerRequest.getId(), customer);
        
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    @Path("/by-id/{id}")
    public Response getCustomerById(@jakarta.ws.rs.PathParam("id") Long id) {
        return customerManager.getCustomerById(id)
            .map(customer -> Response.ok(customer).build())
            .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
