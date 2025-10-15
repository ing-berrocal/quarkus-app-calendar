package com.calendario.app.controller;

import com.calendario.app.model.Customer;
import com.calendario.app.model.request.CustomerRequest;
import com.calendario.app.service.CustomerManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @GET
    @Path("/by-document/{documentNumber}")
    public Response getCustomerByDocument(@PathParam("documentNumber") String documentNumber) {

        return customerManager.getCustomerByDocumentNumber(documentNumber)
            .map(customer -> Response.ok(customer).build())
            .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build()
            );
    }

    @POST
    public Response addCustomer(CustomerRequest customerRequest) {

        customerManager.getCustomerByDocumentNumber(customerRequest.getDocumentNumber())
            .ifPresent(customer -> {
                throw new IllegalArgumentException("Customer already exists with document number: " + customerRequest.getDocumentNumber());
            });

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
}
