package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.client.brasil_api.BrasilApiClient;
import br.com.matteusmoreno.client.brasil_api.BrasilApiResponse;
import br.com.matteusmoreno.supplier.Supplier;
import br.com.matteusmoreno.supplier.supplier_request.CreateSupplierRequest;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class SupplierMapper {


    private final BrasilApiClient brasilApiClient;
    private final AddressMapper addressMapper;

    @Inject
    public SupplierMapper(BrasilApiClient brasilApiClient, AddressMapper addressMapper) {
        this.brasilApiClient = brasilApiClient;
        this.addressMapper = addressMapper;
    }

    public Supplier toEntity(CreateSupplierRequest request) {
        log.info("Mapping supplier to entity");

        String formattedCnpj = request.cnpj().replace("-", "").replace("/", "");
        BrasilApiResponse brasilApiResponse = brasilApiClient.getSupplier(formattedCnpj);
        Address supplierAddress = addressMapper.toEntity(brasilApiResponse.cep());

        return Supplier.builder()
                .cnpj(request.cnpj())
                .corporateName(brasilApiResponse.razao_social())
                .tradeName(brasilApiResponse.nome_fantasia())
                .phone(brasilApiResponse.ddd_telefone_1())
                .registrationStatus(brasilApiResponse.descricao_situacao_cadastral())
                .email(brasilApiResponse.email())
                .legalNature(brasilApiResponse.natureza_juridica())
                .address(supplierAddress)
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();
    }
}
