package br.com.matteusmoreno.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotNull(message = "ID is required")
        Long id,
        String name,
        String description,
        BigDecimal purchasePrice,
        BigDecimal salePrice,
        String manufacturer,
        Integer quantity) {
}
