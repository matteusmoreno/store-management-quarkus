package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.employee.Employee;
import br.com.matteusmoreno.employee.employee_request.CreateEmployeeRequest;
import br.com.matteusmoreno.utils.AppUtils;
import br.com.matteusmoreno.utils.Validation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class EmployeeMapper {

    private final AddressMapper addressMapper;
    private final AppUtils appUtils;
    private final Validation validation;

    @Inject
    public EmployeeMapper(AddressMapper addressMapper, AppUtils appUtils, Validation validation) {
        this.addressMapper = addressMapper;
        this.appUtils = appUtils;
        this.validation = validation;
    }

    public Employee toEntity(CreateEmployeeRequest request) {
        log.info("Mapping employee to entity");

        validation.validateEntryDuplicity(request.email(), request.cpf(), request.phone());

        return Employee.builder()
                .name(request.name())
                .birthDate(request.birthDate())
                .age(appUtils.ageCalculator(request.birthDate()))
                .phone(request.phone())
                .salary(request.salary())
                .role(request.role())
                .email(request.email())
                .cpf(request.cpf())
                .address(addressMapper.toEntity(request.zipcode()))
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();
    }
}
