package com.calendario.app.service;

import com.calendario.app.model.Employee;
import com.calendario.persistence.employee.EmployeeEntity;
import com.calendario.persistence.employee.EmployeeRepository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmployeeResource {

    private final EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> listEmployees() {
        return employeeRepository.listAll().stream()
                .map(e -> new Employee(e.getId(), e.getFullName()))
                .toList();
    }

    @Transactional
    public boolean updateEmployeeName(Long id, String fullName) {
        EmployeeEntity entity = employeeRepository.findById(id);
        if (entity == null) {
            return false;
        }
        entity.setFullName(fullName);
        return true;
    }

    @Transactional
    public Long addEmployee(Employee employee) {
        // TODO Auto-generated method stub

        var entity = new EmployeeEntity();

        entity.setFullName(employee.fullName());
        entity.setState(Boolean.TRUE);

        employeeRepository.persist(entity);
        return entity.getId();
    }

}
