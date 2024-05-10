package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Customer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@JsonPropertyOrder({
        "id",
        "name",
        "birthDate",
        "age",
        "phone",
        "salary",
        "role",
        "email",
        "cpf",
        "address",
        "createdAt",
        "updatedAt",
        "deletedAt",
        "active"
})
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
