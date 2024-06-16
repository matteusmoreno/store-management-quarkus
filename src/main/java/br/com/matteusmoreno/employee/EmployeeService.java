package br.com.matteusmoreno.employee;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.employee.employee_request.CreateEmployeeRequest;
import br.com.matteusmoreno.employee.employee_request.UpdateEmployeeRequest;
import br.com.matteusmoreno.mapper.AddressMapper;
import br.com.matteusmoreno.mapper.EmployeeMapper;
import br.com.matteusmoreno.utils.AppUtils;
import br.com.matteusmoreno.utils.Validation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AddressMapper addressMapper;
    private final Validation validation;
    private final AppUtils appUtils;

    @Inject
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AddressMapper addressMapper, Validation validation, AppUtils appUtils) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.addressMapper = addressMapper;
        this.validation = validation;
        this.appUtils = appUtils;
    }


    @Transactional
    public Employee createEmployee(CreateEmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);

        employeeRepository.save(employee);
        return employee;
    }

    public Employee employeeDetails(UUID id) {
        return employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Employee updateEmployee(UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(request.id()).orElseThrow(NoSuchElementException::new);
        if (request.name() != null) {
            employee.setName(request.name());
        }
        if (request.birthDate() != null) {
            employee.setBirthDate(request.birthDate());
            employee.setAge(appUtils.ageCalculator(request.birthDate()));
        }
        if (request.phone() != null) {
            validation.validatePhoneDuplicity(request.phone());
            employee.setPhone(request.phone());
        }
        if (request.salary() != null) {
            employee.setSalary(request.salary());
        }
        if (request.role() != null) {
            employee.setRole(request.role());
        }
        if (request.email() != null) {
            validation.validateEmailDuplicity(request.email());
            employee.setEmail(request.email());
        }
        if (request.cpf() != null) {
            validation.validateCpfDuplicity(request.cpf());
            employee.setCpf(request.cpf());
        }
        if (request.zipcode() != null) {
            employee.setAddress(addressMapper.toEntity(request.zipcode()));
        }

        employee.setUpdatedAt(LocalDateTime.now());
        employeeRepository.save(employee);

        return employee;
    }

    @Transactional
    public void disableEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        employee.setActive(false);
        employee.setDeletedAt(LocalDateTime.now());

        employeeRepository.save(employee);
    }

    @Transactional
    public Employee enableCustomer(UUID id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        employee.setActive(true);
        employee.setDeletedAt(null);
        employee.setUpdatedAt(LocalDateTime.now());

        employeeRepository.save(employee);

        return employee;
    }
}
