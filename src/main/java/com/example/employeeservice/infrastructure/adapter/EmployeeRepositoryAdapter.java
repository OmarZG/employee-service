package com.example.employeeservice.infrastructure.adapter;

import com.example.employeeservice.domain.model.Employee;
import com.example.employeeservice.domain.port.EmployeeRepositoryPort;
import com.example.employeeservice.infrastructure.entity.EmployeeEntity;
import com.example.employeeservice.infrastructure.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeRepository employeeRepository;

    public EmployeeRepositoryAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = toEntity(employee);
        return toDomain(employeeRepository.save(entity));
    }

    @Override
    public List<Employee> saveAll(List<Employee> employees) {
        List<EmployeeEntity> entities = employees.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return employeeRepository.saveAll(entities).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    // Convert domain model to entity
    private EmployeeEntity toEntity(Employee employee) {
        return EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .secondName(employee.getSecondName())
                .paternalLastName(employee.getPaternalLastName())
                .maternalLastName(employee.getMaternalLastName())
                .age(employee.getAge())
                .sex(employee.getSex())
                .dateOfBirth(employee.getDateOfBirth())
                .position(employee.getPosition())
                .build();
    }

    // Convert entity to domain model
    private Employee toDomain(EmployeeEntity entity) {
        return Employee.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .secondName(entity.getSecondName())
                .paternalLastName(entity.getPaternalLastName())
                .maternalLastName(entity.getMaternalLastName())
                .age(entity.getAge())
                .sex(entity.getSex())
                .dateOfBirth(entity.getDateOfBirth())
                .position(entity.getPosition())
                .build();
    }
}
