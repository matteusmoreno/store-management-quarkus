package br.com.matteusmoreno.supplier.supplier_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UpdateSupplierRequest(
        @NotNull(message = "ID is required")
        UUID id,
        String tradeName,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{9}", message = "Invalid phone number format. Correct format is (XX)XXXXXXXXX")
        String phone,
        @Email(message = "Invalid email format")
        String email,
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid CEP format. Correct format is XXXXX-XXX")
        String zipcode) {
}
