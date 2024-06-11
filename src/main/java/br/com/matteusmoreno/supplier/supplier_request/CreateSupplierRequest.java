package br.com.matteusmoreno.supplier.supplier_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateSupplierRequest(
        @NotBlank(message = "CNPJ is required")
        @Pattern(regexp = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14})$", message = "Invalid CNPJ format. Correct formats are XX.XXX.XXX/XXXX-XX or XXXXXXXXXXXXXX")
        String cnpj) {
}
