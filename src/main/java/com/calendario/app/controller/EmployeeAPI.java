package com.calendario.app.controller;

import com.calendario.app.model.Employee;
import com.calendario.app.model.request.EmployeeRequest;
import com.calendario.app.service.EmployeeManager;

import jakarta.ws.rs.Consumes;
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

    @POST
    public Response addEmployee(EmployeeRequest employeeRequest) {
        // Add employee

        Employee employee = new Employee(employeeRequest.getFullName());
        employeeManager.createEmployee(employee);

        return Response.status(Response.Status.CREATED).build();
    }
}
