package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.customer.Customer;
import br.com.matteusmoreno.customer.customer_request.CreateCustomerRequest;
import br.com.matteusmoreno.utils.AppUtils;
import br.com.matteusmoreno.utils.Validation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class CustomerMapper {

    private final AppUtils appUtils;
    private final AddressMapper addressMapper;
    private final Validation validation;

    @Inject
    public CustomerMapper(AppUtils appUtils, AddressMapper addressMapper, Validation validation) {
        this.appUtils = appUtils;
        this.addressMapper = addressMapper;
        this.validation = validation;
    }

    public Customer toEntity(CreateCustomerRequest request) {
        log.info("Mapping customer to entity");

        validation.validateEntryDuplicity(request.email(), request.cpf(), request.phone());

        return Customer.builder()
                .name(request.name())
                .birthDate(request.birthDate())
                .age(appUtils.ageCalculator(request.birthDate()))
                .phone(request.phone())
                .email(request.email())
                .cpf(request.cpf())
                .address(addressMapper.toEntity(request.zipcode()))
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

    }
}
