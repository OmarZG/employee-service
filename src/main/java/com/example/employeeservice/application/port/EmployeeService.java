package com.example.employeeservice.application.port;

import com.example.employeeservice.domain.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee addEmployee(Employee employee);
    List<Employee> addEmployees(List<Employee> employees);
}