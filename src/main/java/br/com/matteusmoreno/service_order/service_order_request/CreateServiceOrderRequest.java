package br.com.matteusmoreno.service_order.service_order_request;

import br.com.matteusmoreno.service_order_product.service_order_product_request.CreateServiceOrderProductRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateServiceOrderRequest(
        @NotNull(message = "Customer ID is required")
        UUID customerId,
        @NotNull(message = "Employee ID is required")
        UUID employeeId,
        List<CreateServiceOrderProductRequest> serviceOrderProducts,
        @NotNull(message = "Labor Price is required")
        @PositiveOrZero(message = "Labor Price must me positive or zero")
        BigDecimal laborPrice) {
}
