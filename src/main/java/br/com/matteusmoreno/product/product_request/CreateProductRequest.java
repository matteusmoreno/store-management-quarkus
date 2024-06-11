package br.com.matteusmoreno.product.product_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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
        String manufacturer,
        @NotNull(message = "Quantity Price is required")
        @PositiveOrZero(message = "The quantity must be a positive number or zero.")
        Integer quantity) {
}
