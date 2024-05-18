package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Supplier;

import java.time.LocalDateTime;
import java.util.UUID;

public record SupplierDetailsResponse(
        UUID id,
        String corporateName,
        String tradeName,
        String cnpj,
        String phone,
        String registrationStatus,
        String email,
        String legalNature,
        Address address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public SupplierDetailsResponse(Supplier supplier) {
        this(supplier.getId(), supplier.getCorporateName(), supplier.getTradeName(), supplier.getCnpj(), supplier.getPhone(),
                supplier.getRegistrationStatus(), supplier.getEmail(), supplier.getLegalNature(), supplier.getAddress(),
                supplier.getCreatedAt(), supplier.getUpdatedAt(), supplier.getDeletedAt(), supplier.getActive());
    }
}
