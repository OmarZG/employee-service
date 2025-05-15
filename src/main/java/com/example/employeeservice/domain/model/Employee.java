package com.example.employeeservice.domain.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Employee {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String secondName;

    @NotBlank(message = "Paternal last name is required")
    private String paternalLastName;

    private String maternalLastName;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private Integer age;

    @NotBlank(message = "Sex is required")
    private String sex;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Position is required")
    private String position;


}