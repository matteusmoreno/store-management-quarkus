package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDetailsResponse(
        Long id,
        String name,
        String description,
        BigDecimal purchasePrice,
        BigDecimal salePrice,
        String manufacturer,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public ProductDetailsResponse(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPurchasePrice(),
                product.getSalePrice(), product.getManufacturer(), product.getQuantity(), product.getCreatedAt(),
                product.getUpdatedAt(), product.getDeletedAt(), product.getActive());
    }
}
