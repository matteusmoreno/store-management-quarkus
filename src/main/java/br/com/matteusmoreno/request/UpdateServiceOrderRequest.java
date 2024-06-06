package br.com.matteusmoreno.request;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.domain.Employee;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record UpdateServiceOrderRequest(
        @NotNull(message = "ID is required")
        UUID id,
        Customer customer,
        Employee employee,
        String description,
        Map<Long, Integer> products,
        BigDecimal laborPrice) {
}
