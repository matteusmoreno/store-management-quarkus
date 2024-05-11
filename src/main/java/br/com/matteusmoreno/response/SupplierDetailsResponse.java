package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Supplier;

import java.time.LocalDateTime;
import java.util.UUID;

public record SupplierDetailsResponse(
        UUID id,
        String name,
        String cnpj,
        String phone,
        String email,
        String site,
        Address address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public SupplierDetailsResponse(Supplier supplier) {
        this(supplier.getId(), supplier.getName(), supplier.getCnpj(), supplier.getPhone(),
                supplier.getEmail(), supplier.getSite(), supplier.getAddress(), supplier.getCreatedAt(),
                supplier.getUpdatedAt(), supplier.getDeletedAt(), supplier.getActive());
    }
}
