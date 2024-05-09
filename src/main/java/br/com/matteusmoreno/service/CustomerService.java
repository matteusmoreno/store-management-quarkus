package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.request.CreateCostumerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public Customer createCustomer(CreateCostumerRequest request) {
        Customer customer = new Customer(request);
        customerRepository.persist(customer);
        return customer;
    }
}
