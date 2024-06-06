package br.com.matteusmoreno.response.service_order_response;

import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.ServiceOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record ServiceOrderDetailsResponse(
        UUID id,
        ServiceOrderCustomerResponse customer,
        ServiceOrderEmployeeResponse employee,
        String description,
        String serviceOrderStatus,
        List<ServiceOrderProductResponse> products,
        BigDecimal laborPrice,
        BigDecimal cost,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt) {

    public ServiceOrderDetailsResponse(ServiceOrder serviceOrder, Map<Product, Integer> productsWithQuantities) {
        this(serviceOrder.getId(),
                new ServiceOrderCustomerResponse(serviceOrder.getCustomer()),
                new ServiceOrderEmployeeResponse(serviceOrder.getEmployee()),
                serviceOrder.getDescription(),
                serviceOrder.getServiceOrderStatus().getDisplayName(),
                productsWithQuantities.entrySet().stream()
                        .map(entry -> new ServiceOrderProductResponse(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()),
                serviceOrder.getLaborPrice(),
                serviceOrder.getCost(),
                serviceOrder.getCreatedAt(),
                serviceOrder.getUpdatedAt(),
                serviceOrder.getDeletedAt());
    }
}
