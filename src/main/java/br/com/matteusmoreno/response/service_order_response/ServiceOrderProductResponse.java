package br.com.matteusmoreno.response.service_order_response;

import br.com.matteusmoreno.domain.Product;

import java.math.BigDecimal;

public record ServiceOrderProductResponse(
        Long id,
        String name,
        String manufacturer,
        int quantity,
        BigDecimal unitSalePrice,
        BigDecimal totalPrice) {

    public ServiceOrderProductResponse(Product product, int quantity) {
        this(product.getId(), product.getName(), product.getManufacturer(), quantity, product.getSalePrice(),
                product.getSalePrice().multiply(BigDecimal.valueOf(quantity)));
    }
}
