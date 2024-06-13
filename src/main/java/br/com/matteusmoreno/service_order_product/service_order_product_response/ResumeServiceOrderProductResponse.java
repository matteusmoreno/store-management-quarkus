package br.com.matteusmoreno.service_order_product.service_order_product_response;

import br.com.matteusmoreno.product.product_response.ResumeProductResponse;
import br.com.matteusmoreno.service_order_product.ServiceOrderProduct;

import java.math.BigDecimal;

public record ResumeServiceOrderProductResponse(
        ResumeProductResponse product,
        Integer quantity,
        BigDecimal unitaryPrice,
        BigDecimal finalPrice) {

    public ResumeServiceOrderProductResponse(ServiceOrderProduct serviceOrderProduct) {
        this(new ResumeProductResponse(serviceOrderProduct.getProduct()), serviceOrderProduct.getQuantity(),
                serviceOrderProduct.getUnitaryPrice(), serviceOrderProduct.getFinalPrice());
    }
}
