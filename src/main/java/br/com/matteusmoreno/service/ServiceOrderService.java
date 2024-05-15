package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.ServiceOrder;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.repository.EmployeeRepository;
import br.com.matteusmoreno.repository.ProductRepository;
import br.com.matteusmoreno.repository.ServiceOrderRepository;
import br.com.matteusmoreno.request.CreateServiceOrderRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ServiceOrderService {

    @Inject
    ServiceOrderRepository serviceOrderRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public ServiceOrder createServiceOrder(CreateServiceOrderRequest request) {
        ServiceOrder serviceOrder = new ServiceOrder(request);

        Customer customer = customerRepository.findByUUID(request.customer());
        Employee employee = employeeRepository.findByUUID(request.employee());
        List<Product> products = productRepository.findAllById(request.products());

        BigDecimal productsPrice = products.stream()
                .map(Product::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        serviceOrder.setCost(productsPrice.add(request.laborPrice()));
        serviceOrder.setCustomer(customer);
        serviceOrder.setEmployee(employee);
        serviceOrder.setProducts(products);

        serviceOrderRepository.persist(serviceOrder);

        return serviceOrder;
    }

    public ServiceOrder serviceOrderDetails(UUID id) {
        return serviceOrderRepository.findByUUID(id);
    }
}
