package br.com.matteusmoreno.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CreateCustomerRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Birth date is required")
        LocalDate birthDate,
        @NotBlank(message = "e-mail is required")
        @Email(message = "Invalid email format")
        String email,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{9}", message = "Invalid phone number format. Correct format is (XX)XXXXXXXXX")
        String phone,
        @NotBlank(message = "CPF is required")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Invalid CPF format. Correct format is XXX.XXX.XXX-XX")
        String cpf,
        @NotBlank
        @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "Invalid CEP format. Correct formats are XXXXX-XXX or XXXXXXXX")
        String zipcode) {
}
