package br.com.matteusmoreno.service;

import br.com.matteusmoreno.constant.ServiceOrderStatus;
import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.ServiceOrder;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.repository.EmployeeRepository;
import br.com.matteusmoreno.repository.ProductRepository;
import br.com.matteusmoreno.repository.ServiceOrderRepository;
import br.com.matteusmoreno.request.CreateServiceOrderRequest;
import br.com.matteusmoreno.request.UpdateServiceOrderRequest;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Inject
    AppUtils appUtils;

    @Transactional
    public ServiceOrder createServiceOrder(CreateServiceOrderRequest request) {
        ServiceOrder serviceOrder = new ServiceOrder(request);

        Customer customer = customerRepository.findById(request.customer()).orElseThrow();
        Employee employee = employeeRepository.findById(request.employee()).orElseThrow();
        List<Product> products = productRepository.findAllById(request.products());
        BigDecimal cost = appUtils.costCalculator(products, request.laborPrice());

        serviceOrder.setCost(cost);
        serviceOrder.setCustomer(customer);
        serviceOrder.setEmployee(employee);
        serviceOrder.setProducts(products);

        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }

    public ServiceOrder serviceOrderDetails(UUID id) {
        return serviceOrderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public ServiceOrder updateServiceOrder(UpdateServiceOrderRequest request) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(request.id()).orElseThrow();

        if (request.customer() != null) {
            serviceOrder.setCustomer(request.customer());
        }
        if (request.employee() != null) {
            serviceOrder.setEmployee(request.employee());
        }
        if (request.description() != null) {
            serviceOrder.setDescription(request.description());
        }
        if (request.products() != null) {
            List<Product> products = productRepository.findAllById(request.products());
            serviceOrder.setProducts(products);
            serviceOrder.setCost(appUtils.costCalculator(products, serviceOrder.getLaborPrice()));
        }
        if (request.laborPrice() != null) {
            serviceOrder.setLaborPrice(request.laborPrice());
            serviceOrder.setCost(appUtils.costCalculator(serviceOrder.getProducts(), request.laborPrice()));
        }

        serviceOrder.setUpdatedAt(LocalDateTime.now());

        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }

    @Transactional
    public ServiceOrder startService(UUID id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id).orElseThrow();

        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.IN_PROGRESS);
        serviceOrderRepository.save(serviceOrder);

        return serviceOrder;
    }

    @Transactional
    public ServiceOrder completeService(UUID id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id).orElseThrow();

        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.COMPLETED);

        return serviceOrder;
    }

    @Transactional
    public ServiceOrder cancelService(UUID id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id).orElseThrow();

        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.CANCELED);

        return serviceOrder;
    }

}
