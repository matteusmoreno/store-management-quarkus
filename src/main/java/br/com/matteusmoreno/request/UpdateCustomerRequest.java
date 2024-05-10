package br.com.matteusmoreno.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateCustomerRequest(
        @NotNull(message = "Coloca a porra do id")
        UUID id,
        String name,
        LocalDate birthDate,
        @Email(message = "Invalid email format")
        String email,
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Invalid CPF format")
        String cpf,
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid CEP format")
        String zipcode) {
}
