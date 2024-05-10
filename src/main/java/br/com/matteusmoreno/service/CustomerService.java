package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.request.CreateCustomerRequest;
import br.com.matteusmoreno.request.UpdateCustomerRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer(request);
        customerRepository.persist(customer);
        return customer;
    }

    public PanacheQuery<Customer> listCustomers(int page, int size) {
        return customerRepository.findAll();
    }

    public Customer customerDetails(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(request.id());

        if (request.name() != null) {
            customer.setName(request.name());
        }

        customerRepository.persist(customer);

        return customer;
    }
}
