package com.calendario.app.service;

import com.calendario.app.model.Employee;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmployeeManager {

    @Inject
    private EmployeeResource employeeResource;

    public List<Employee> listEmployees() {
        return employeeResource.listEmployees();
    }

    public boolean updateEmployeeName(Long id, String fullName) {
        return employeeResource.updateEmployeeName(id, fullName);
    }

    public Long createEmployee(Employee employee) {
        // Create employee
        return employeeResource.addEmployee(employee);
    }
}
