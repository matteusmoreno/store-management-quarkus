package br.com.matteusmoreno.response.service_order_response;

import br.com.matteusmoreno.domain.Customer;

import java.util.UUID;

public record ServiceOrderCustomerResponse(
        UUID id,
        String name,
        String email,
        String phone) {

    public ServiceOrderCustomerResponse(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }
}

