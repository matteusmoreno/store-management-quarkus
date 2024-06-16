package br.com.matteusmoreno.customer;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.customer.customer_request.CreateCustomerRequest;
import br.com.matteusmoreno.customer.customer_request.UpdateCustomerRequest;
import br.com.matteusmoreno.exception.exception_class.DuplicateEntryException;
import br.com.matteusmoreno.mapper.AddressMapper;
import br.com.matteusmoreno.mapper.CustomerMapper;
import br.com.matteusmoreno.utils.AppUtils;
import br.com.matteusmoreno.utils.Validation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final Validation validation;
    private final AppUtils appUtils;

    @Inject
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, AddressMapper addressMapper, Validation validation, AppUtils appUtils) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.validation = validation;
        this.appUtils = appUtils;
    }

    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);

        customerRepository.save(customer);
        return customer;
    }

    public Customer customerDetailsById(UUID id) {
        return customerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Customer> customerDetailsByNeighborhood(String neighborhood) {
        return customerRepository.findByAddressNeighborhoodContainingIgnoreCase(neighborhood);

    }

    @Transactional
    public Customer updateCustomer(UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(request.id()).orElseThrow(NoSuchElementException::new);

        if (request.name() != null) {
            customer.setName(request.name());
        }
        if (request.birthDate() != null) {
            customer.setBirthDate(request.birthDate());
            customer.setAge(appUtils.ageCalculator(request.birthDate()));
        }
        if (request.email() != null) {
            validation.validateEmailDuplicity(request.email());
            customer.setEmail(request.email());
        }
        if (request.phone() != null) {
            validation.validatePhoneDuplicity(request.phone());
            customer.setPhone(request.phone());
        }
        if (request.cpf() != null) {
            validation.validateCpfDuplicity(request.cpf());
            customer.setCpf(request.cpf());
        }
        if (request.zipcode() != null) {
            customer.setAddress(addressMapper.toEntity(request.zipcode()));
        }

        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        appUtils.sendEmail("email-update-customer.txt", customer.getEmail(), "Updated Customer", customer.getName());

        return customer;
    }

    @Transactional
    public void disableCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        customer.setActive(false);
        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);
        appUtils.sendEmail("email-disable-customer.txt", customer.getEmail(), "Disable Customer", customer.getName());
    }

    @Transactional
    public Customer enableCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        customer.setActive(true);
        customer.setDeletedAt(null);
        customer.setUpdatedAt(LocalDateTime.now());

        customerRepository.save(customer);
        appUtils.sendEmail("email-enable-customer.txt", customer.getEmail(), "Enable Customer", customer.getName());

        return customer;
    }

}
