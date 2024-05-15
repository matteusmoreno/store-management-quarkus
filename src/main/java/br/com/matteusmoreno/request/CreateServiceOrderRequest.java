package br.com.matteusmoreno.request;

import br.com.matteusmoreno.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateServiceOrderRequest(
        UUID customer,
        UUID employee,
        String description,
        List<Long> products,
        BigDecimal laborPrice) {
}
