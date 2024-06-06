package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.request.CreateCustomerRequest;
import br.com.matteusmoreno.request.UpdateCustomerRequest;
import br.com.matteusmoreno.utils.AppUtils;
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
    private final AppUtils appUtils;

    @Inject
    public CustomerService(CustomerRepository customerRepository, AppUtils appUtils) {
        this.customerRepository = customerRepository;
        this.appUtils = appUtils;
    }

    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {
        Address address = appUtils.setAddressAttributes(request.zipcode());
        Integer age = appUtils.ageCalculator(request.birthDate());

        Customer customer = new Customer(request);
        customer.setAge(age);
        customer.setAddress(address);

        customerRepository.save(customer);
        appUtils.sendEmail("email-create-customer.txt", customer.getEmail(), "Welcome to Store Management", customer.getName());

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
            customer.setEmail(request.email());
        }
        if (request.phone() != null) {
            customer.setPhone(request.phone());
        }
        if (request.cpf() != null) {
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
