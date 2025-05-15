package com.example.employeeservice.infrastructure.controller;

import com.example.employeeservice.application.port.EmployeeService;
import com.example.employeeservice.domain.model.Employee;
import com.example.employeeservice.infrastructure.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeControllerTest.MockConfig.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturnAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void shouldReturnNotFoundWhenEmployeeNotExists() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        Employee input = new Employee(null, "John", "Doe", "Smith", "Johnson", 30, "Male",
                LocalDate.of(1991, 1, 1), "Developer");

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldCreateMultipleEmployees() throws Exception {
        List<Employee> inputList = List.of(employee);
        when(employeeService.addEmployees(anyList())).thenReturn(inputList);

        mockMvc.perform(post("/api/employees/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldReturnNotFoundOnUpdateIfNotExists() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(Employee.class)))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundOnDeleteIfNotExists() throws Exception {
        doThrow(new ResourceNotFoundException("Not found")).when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public EmployeeService employeeService() {
            return Mockito.mock(EmployeeService.class);
        }
    }
}
