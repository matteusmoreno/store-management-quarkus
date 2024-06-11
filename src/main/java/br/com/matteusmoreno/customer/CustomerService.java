package br.com.matteusmoreno.customer;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.customer.customer_request.CreateCustomerRequest;
import br.com.matteusmoreno.customer.customer_request.UpdateCustomerRequest;
import br.com.matteusmoreno.exception.exception_class.DuplicateEntryException;
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
    private final Validation validation;
    private final AppUtils appUtils;

    @Inject
    public CustomerService(CustomerRepository customerRepository, Validation validation, AppUtils appUtils) {
        this.customerRepository = customerRepository;
        this.validation = validation;
        this.appUtils = appUtils;
    }

    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {

        validation.validateEntryDuplicity(request.email(), request.cpf(), request.phone());

        Address address = appUtils.setAddressAttributes(request.zipcode());
        Integer age = appUtils.ageCalculator(request.birthDate());

        Customer customer = new Customer(request);
        customer.setAge(age);
        customer.setAddress(address);

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
            customer.setAddress(appUtils.setAddressAttributes(request.zipcode()));
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
