package br.com.matteusmoreno.request;

import jakarta.validation.constraints.NotBlank;

public record CreateSupplierRequest(
        @NotBlank(message = "CNPJ is required")
        String cnpj) {
}
