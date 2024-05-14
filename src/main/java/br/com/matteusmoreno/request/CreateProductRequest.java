package br.com.matteusmoreno.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Purchase Price is required")
        BigDecimal purchasePrice,
        @NotNull(message = "Sale Price is required")
        BigDecimal salePrice,
        @NotBlank(message = "Manufacturer is required")
        String manufacturer) {
}
