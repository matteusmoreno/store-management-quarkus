package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.ServiceOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ServiceOrderDetailsResponse(
        UUID id,
        Customer customer,
        Employee employee,
        String description,
        String serviceOrderStatus,
        List<Product> products,
        BigDecimal laborPrice,
        BigDecimal cost,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt) {

    public ServiceOrderDetailsResponse(ServiceOrder serviceOrder) {
        this(serviceOrder.getId(), serviceOrder.getCustomer(), serviceOrder.getEmployee(), serviceOrder.getDescription(),
                serviceOrder.getServiceOrderStatus().getDisplayName(), serviceOrder.getProducts(), serviceOrder.getLaborPrice(),
                serviceOrder.getCost(), serviceOrder.getCreatedAt(), serviceOrder.getUpdatedAt(), serviceOrder.getDeletedAt());
    }
}
