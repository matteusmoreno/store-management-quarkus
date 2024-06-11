package br.com.matteusmoreno.customer.customer_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateCustomerRequest(
        @NotNull(message = "ID is required")
        UUID id,
        String name,
        LocalDate birthDate,
        @Email(message = "Invalid email format")
        String email,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{9}", message = "Invalid phone number format. Correct format is (XX)XXXXXXXXX")
        String phone,
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Invalid CPF format. Correct format is XXX.XXX.XXX-XX")
        String cpf,
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid CEP format. Correct format is XXXXX-XXX")
        String zipcode) {
}
