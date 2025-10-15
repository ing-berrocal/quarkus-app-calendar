package com.calendario.app.service;

import com.calendario.app.model.Employee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmployeeManager {

    @Inject
    private EmployeeResource employeeResource;

    public void createEmployee(Employee employee) {
        // Create employee
        employeeResource.addEmployee(employee);
    }
}
