package br.com.matteusmoreno.employee.employee_request;

import br.com.matteusmoreno.employee.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeRequest(
        @NotNull(message = "ID is required")
        UUID id,
        String name,
        LocalDate birthDate,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{9}", message = "Invalid phone number format. Correct format is (XX)XXXXXXXXX")
        String phone,
        BigDecimal salary,
        EmployeeRole role,
        @Email(message = "Invalid email format")
        String email,
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Invalid CPF format. Correct format is XXX.XXX.XXX-XX")
        String cpf,
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid CEP format. Correct format is XXXXX-XXX")
        String zipcode) {
}
