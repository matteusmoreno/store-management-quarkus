package br.com.matteusmoreno.service_order_product.service_order_product_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateServiceOrderProductRequest(
        @NotNull(message = "Product is required")
        Long productId, 
        @NotNull(message = "Product quantity is required")
        @Positive(message = "Product quantity must be positive")
        Integer quantity) {
}
