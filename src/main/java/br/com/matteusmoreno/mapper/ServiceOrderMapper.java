package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.customer.CustomerRepository;
import br.com.matteusmoreno.employee.EmployeeRepository;
import br.com.matteusmoreno.exception.exception_class.OutOfStockException;
import br.com.matteusmoreno.product.Product;
import br.com.matteusmoreno.product.ProductRepository;
import br.com.matteusmoreno.service_order.ServiceOrder;
import br.com.matteusmoreno.service_order.constant.ServiceOrderStatus;
import br.com.matteusmoreno.service_order.service_order_request.CreateServiceOrderRequest;
import br.com.matteusmoreno.service_order_product.ServiceOrderProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@ApplicationScoped
public class ServiceOrderMapper {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceOrderProductService serviceOrderProductService;
    private final ProductRepository productRepository;

    @Inject
    public ServiceOrderMapper(CustomerRepository customerRepository, EmployeeRepository employeeRepository, ServiceOrderProductService serviceOrderProductService, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.serviceOrderProductService = serviceOrderProductService;
        this.productRepository = productRepository;
    }


    public ServiceOrder toEntity(CreateServiceOrderRequest request) {
        log.info("Mapping Create service order");
            
        request.serviceOrderProducts().forEach(serviceOrderProduct -> {
            Product product = productRepository.findById(serviceOrderProduct.productId()).orElseThrow();
            if (serviceOrderProduct.quantity() > product.getQuantity()) {
                throw new OutOfStockException("Product Out of Stock");
            }
        });

        BigDecimal productsPrice = request.serviceOrderProducts().stream()
                .map(serviceOrderProduct -> {
                    Product product = productRepository.findById(serviceOrderProduct.productId()).orElseThrow();
                    return product.getSalePrice().multiply(BigDecimal.valueOf(serviceOrderProduct.quantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return ServiceOrder.builder()
                .customer(customerRepository.findById(request.customerId()).orElseThrow(NoSuchElementException::new))
                .employee(employeeRepository.findById(request.employeeId()).orElseThrow(NoSuchElementException::new))
                .serviceOrderProducts(serviceOrderProductService.createServiceOrderProduct(request.serviceOrderProducts()))
                .laborPrice(request.laborPrice())
                .totalCost(productsPrice.add(request.laborPrice()))
                .createdAt(LocalDateTime.now())
                .serviceOrderStatus(ServiceOrderStatus.PENDING)
                .build();
    }
}
