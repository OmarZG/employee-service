package com.example.employeeservice.application.service;

import com.example.employeeservice.application.port.EmployeeService;
import com.example.employeeservice.domain.model.Employee;
import com.example.employeeservice.domain.port.EmployeeRepositoryPort;
import com.example.employeeservice.infrastructure.exception.ResourceNotFoundException;
import com.example.employeeservice.util.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public EmployeeServiceImpl(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepositoryPort.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.EMPLOYEE_NOT_FOUND + id));
    }

    @Override
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = getEmployeeById(id);
        updated.setId(existing.getId());
        return employeeRepositoryPort.save(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (employeeRepositoryPort.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(Constants.EMPLOYEE_NOT_FOUND + id);
        }
        employeeRepositoryPort.deleteById(id);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepositoryPort.save(employee);
    }

    @Override
    public List<Employee> addEmployees(List<Employee> employees) {
        return employeeRepositoryPort.saveAll(employees);
    }
}
