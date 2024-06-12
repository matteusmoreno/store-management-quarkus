package br.com.matteusmoreno.product.product_response;

import br.com.matteusmoreno.product.Product;

import java.math.BigDecimal;

public record ResumeProductResponse(
        String name,
        String manufacturer,
        BigDecimal salePrice) {

    public ResumeProductResponse(Product product) {
        this(product.getName(), product.getManufacturer(), product.getSalePrice());
    }
}
