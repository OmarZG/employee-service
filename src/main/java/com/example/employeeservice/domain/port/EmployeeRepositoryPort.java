package com.example.employeeservice.domain.port;

import com.example.employeeservice.domain.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    List<Employee> saveAll(List<Employee> employees);
    void deleteById(Long id);
}