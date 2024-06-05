package br.com.matteusmoreno.request;

import br.com.matteusmoreno.constant.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateEmployeeRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Birth date is required")
        LocalDate birthDate,
        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "\\(\\d{2}\\)\\d{9}", message = "Invalid phone number format. Correct format is (XX)XXXXXXXXX")
        String phone,
        @NotNull(message = "Salary is required")
        BigDecimal salary,
        @NotNull(message = "Employee Role is required")
        EmployeeRole role,
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "CPF is required")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Invalid CPF format. Correct format is XXX.XXX.XXX-XX")
        String cpf,
        @NotBlank(message = "Zipcode is required")
        @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "Invalid CEP format. Correct formats are XXXXX-XXX or XXXXXXXX")
        String zipcode) {
}
