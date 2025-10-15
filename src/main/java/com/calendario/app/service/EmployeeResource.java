package com.calendario.app.service;

import com.calendario.app.model.Employee;
import com.calendario.persistence.employee.EmployeeEntity;
import com.calendario.persistence.employee.EmployeeRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmployeeResource {

    private final EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void addEmployee(Employee employee) {
        // TODO Auto-generated method stub

        var entity = new EmployeeEntity();

        entity.setFullName(employee.fullName());
        entity.setState(Boolean.TRUE);

        employeeRepository.persist(entity);
    }

}
