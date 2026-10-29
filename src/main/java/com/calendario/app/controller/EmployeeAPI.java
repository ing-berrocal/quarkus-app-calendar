package com.calendario.app.controller;

import com.calendario.app.model.Employee;
import com.calendario.app.model.request.EmployeeRequest;
import com.calendario.app.service.EmployeeManager;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeAPI {
    
    private final EmployeeManager employeeManager;

    public EmployeeAPI(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    @GET
    public Response listEmployees() {
        return Response.ok(employeeManager.listEmployees()).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployeeName(@PathParam("id") Long id, @Valid EmployeeRequest employeeRequest) {
        if (!employeeManager.updateEmployeeName(id, employeeRequest.getFullName())) {
            throw new NotFoundException("Employee not found: " + id);
        }
        return Response.ok(new Employee(id, employeeRequest.getFullName())).build();
    }

    @POST
    public Response addEmployee(@Valid EmployeeRequest employeeRequest) {
        // Add employee

        Employee employee = new Employee(null, employeeRequest.getFullName());
        Long id = employeeManager.createEmployee(employee);

        return Response.status(Response.Status.CREATED)
                .entity(new Employee(id, employee.fullName()))
                .build();
    }
}
