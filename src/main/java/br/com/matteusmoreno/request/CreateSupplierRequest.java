package br.com.matteusmoreno.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateSupplierRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "CNPJ is required")
        @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "Formato de CNPJ inválido. O formato correto é XX.XXX.XXX/XXXX-XX")
        String cnpj,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{9}", message = "Invalid phone number format. Correct format is (XX)XXXXXXXXX")
        String phone,
        @Email(message = "Invalid email format")
        String email,
        String site,
        @NotBlank
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid CEP format. Correct format is XXXXX-XXX")
        String zipcode) {
}
