package br.com.matteusmoreno.service_order;

import br.com.matteusmoreno.customer.Customer;
import br.com.matteusmoreno.customer.CustomerRepository;
import br.com.matteusmoreno.employee.Employee;
import br.com.matteusmoreno.employee.EmployeeRepository;
import br.com.matteusmoreno.service_order.constant.ServiceOrderStatus;
import br.com.matteusmoreno.service_order_product.ServiceOrderProduct;
import br.com.matteusmoreno.service_order_product.ServiceOrderProductService;
import br.com.matteusmoreno.service_order.service_order_request.CreateServiceOrderRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceOrderProductService serviceOrderProductService;

    @Inject
    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, ServiceOrderProductService serviceOrderProductService) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.serviceOrderProductService = serviceOrderProductService;
    }

    public ServiceOrder createServiceOrder(CreateServiceOrderRequest request) {

        Customer customer = customerRepository.findById(request.customerId()).orElseThrow(NoSuchElementException::new);
        Employee employee = employeeRepository.findById(request.employeeId()).orElseThrow(NoSuchElementException::new);
        List<ServiceOrderProduct> serviceOrderProductList = serviceOrderProductService.createServiceOrderProduct(request.serviceOrderProducts());
        BigDecimal totalPriceServiceOrderProducts = serviceOrderProductList.stream()
                .map(ServiceOrderProduct::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setCustomer(customer);
        serviceOrder.setEmployee(employee);
        serviceOrder.setProducts(serviceOrderProductList);
        serviceOrder.setCreatedAt(LocalDateTime.now());
        serviceOrder.setServiceOrderStatus(ServiceOrderStatus.PENDING);
        serviceOrder.setLaborPrice(request.laborPrice());
        serviceOrder.setTotalCost(totalPriceServiceOrderProducts.add(serviceOrder.getLaborPrice()));

        serviceOrderRepository.persist(serviceOrder);

        return serviceOrder;
    }
}
