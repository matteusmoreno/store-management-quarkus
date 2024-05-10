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
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    AppUtils appUtils;

    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {
        Address address = appUtils.setAddressAttributes(request.zipcode());
        Integer age = appUtils.ageCalculator(request.birthDate());

        Customer customer = new Customer(request);
        customer.setAge(age);
        customer.setAddress(address);

        customerRepository.persist(customer);
        return customer;
    }

    public Customer customerDetails(UUID id) {
        return customerRepository.findByUUID(id);
    }

    @Transactional
    public Customer updateCustomer(UpdateCustomerRequest request) {
        Customer customer = customerRepository.findByUUID(request.id());

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
        if (request.cpf() != null) {
            customer.setCpf(request.cpf());
        }
        if (request.zipcode() != null) {
            customer.setAddress(appUtils.setAddressAttributes(request.zipcode()));
        }

        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.persist(customer);

        return customer;
    }

    @Transactional
    public void disableCustomer(UUID id) {
        Customer customer = customerRepository.findByUUID(id);
        customer.setActive(false);
        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.persist(customer);
    }

    @Transactional
    public Customer enableCustomer(UUID id) {
        Customer customer = customerRepository.findByUUID(id);
        customer.setActive(true);
        customer.setDeletedAt(null);
        customer.setUpdatedAt(LocalDateTime.now());

        return customer;
    }
}
