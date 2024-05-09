package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Customer;

import java.time.LocalDateTime;

public record CustomerDetailsResponse(
        Long id,
        String name,
        LocalDateTime createdAt) {

    public CustomerDetailsResponse(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getCreatedAt());
    }
}
