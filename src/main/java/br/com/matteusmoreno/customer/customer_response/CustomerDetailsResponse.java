package br.com.matteusmoreno.customer.customer_response;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.customer.Customer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDetailsResponse(
        UUID id,
        String name,
        LocalDate birthDate,
        Integer age,
        String email,
        Address address,
        String cpf,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public CustomerDetailsResponse(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getBirthDate(), customer.getAge(),
                customer.getEmail(), customer.getAddress(), customer.getCpf(), customer.getCreatedAt(),
                customer.getUpdatedAt(), customer.getDeletedAt(), customer.getActive());
    }
}
