package br.com.matteusmoreno.request;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record CreateServiceOrderRequest(
        UUID customer,
        UUID employee,
        String description,
        Map<Long, Integer> products,
        BigDecimal laborPrice) {
}
