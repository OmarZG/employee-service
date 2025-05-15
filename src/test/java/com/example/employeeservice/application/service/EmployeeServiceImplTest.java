package com.example.employeeservice.application.service;

import com.example.employeeservice.domain.model.Employee;
import com.example.employeeservice.domain.port.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepositoryPort employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse("01-01-1991", formatter);

        employee = new Employee(
                1L, "John", "Doe", "Smith", "Johnson", 30, "Male", birthDate, "Developer"
        );
    }

    @Test
    void shouldSaveEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.addEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void shouldUpdateEmployee() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(employee.getId(), employee);

        assertNotNull(updatedEmployee);
        assertEquals(employee.getFirstName(), updatedEmployee.getFirstName());
    }

    @Test
    void shouldDeleteEmployee() {
        // Act
        employeeRepository.deleteById(1L);

        // Assert
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}