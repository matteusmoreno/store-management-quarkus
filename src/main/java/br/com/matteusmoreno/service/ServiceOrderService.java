package br.com.matteusmoreno.service;

import br.com.matteusmoreno.constant.ServiceOrderStatus;
import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.ServiceOrder;
import br.com.matteusmoreno.exception.OutOfStockException;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.repository.EmployeeRepository;
import br.com.matteusmoreno.repository.ProductRepository;
import br.com.matteusmoreno.repository.ServiceOrderRepository;
import br.com.matteusmoreno.request.CreateServiceOrderRequest;
import br.com.matteusmoreno.request.UpdateServiceOrderRequest;
import br.com.matteusmoreno.response.service_order_response.ServiceOrderDetailsResponse;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final AppUtils appUtils;

    @Inject
    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, AppUtils appUtils) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.appUtils = appUtils;
    }

    @Transactional
    public ServiceOrderDetailsResponse createServiceOrder(CreateServiceOrderRequest request) {
        ServiceOrder serviceOrder = new ServiceOrder(request);

        Customer customer = customerRepository.findById(request.customer()).orElseThrow(NoSuchElementException::new);
        Employee employee = employeeRepository.findById(request.employee()).orElseThrow(NoSuchElementException::new);
        // Converte os produtos e suas quantidades da requisição em um mapa de produtos e suas respectivas quantidades
        Map<Product, Integer> productsWithQuantities = request.products().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> productRepository.findById(entry.getKey()).orElseThrow(NoSuchElementException::new),
                        Map.Entry::getValue
                ));

        // Calcula o custo total da ordem de serviço com base nos produtos e no preço da mão de obra
        BigDecimal cost = appUtils.costCalculator(productsWithQuantities, request.laborPrice());

        // Inicializa uma mensagem para produtos fora de estoque
        StringBuilder outOfStockMessage = new StringBuilder("The following products are out of stock:");

        // Flag para indicar se produtos fora de estoque foram encontrados
        boolean outOfStockFound = false;

        // Verifica se algum produto tem estoque insuficiente
        for (Map.Entry<Product, Integer> entry : productsWithQuantities.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Se a quantidade solicitada for maior que a quantidade em estoque, adiciona o produto à mensagem
            if (product.getQuantity() < quantity) {
                outOfStockFound = true;
                outOfStockMessage.append("\n- Product: ").append(product.getName()).append(", Current Quantity: ").append(product.getQuantity());
            }

            // Reduz a quantidade em estoque do produto
            product.setQuantity(product.getQuantity() - quantity);
        }

        // Se produtos fora de estoque foram encontrados, lança uma exceção com a mensagem
        if (outOfStockFound) {
            throw new OutOfStockException(outOfStockMessage.toString());
        }

        // Salva as alterações nos produtos no repositório
        productRepository.saveAll(productsWithQuantities.keySet());

        // Configura os detalhes da ordem de serviço
        serviceOrder.setCost(cost);
        serviceOrder.setCustomer(customer);
        serviceOrder.setEmployee(employee);
        serviceOrder.setProducts(new ArrayList<>(productsWithQuantities.keySet()));

        // Salva a ordem de serviço no repositório
        serviceOrderRepository.save(serviceOrder);

        // Retorna um objeto de resposta contendo os detalhes da ordem de serviço e os produtos com suas quantidades
        return new ServiceOrderDetailsResponse(serviceOrder, productsWithQuantities);
    }


    public ServiceOrder serviceOrderDetails(UUID id) {
        return serviceOrderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public ServiceOrderDetailsResponse updateServiceOrder(UpdateServiceOrderRequest request) {
        // Busca a ordem de serviço a ser atualizada pelo ID fornecido na requisição, ou lança uma exceção se não encontrada
        ServiceOrder serviceOrder = serviceOrderRepository.findById(request.id()).orElseThrow();

        // Atualiza os campos da ordem de serviço, se fornecidos na requisição
        if (request.customer() != null) {
            serviceOrder.setCustomer(request.customer());
        }
        if (request.employee() != null) {
            serviceOrder.setEmployee(request.employee());
        }
        if (request.description() != null) {
            serviceOrder.setDescription(request.description());
        }

        // Inicializa um mapa vazio para armazenar os produtos atualizados com suas quantidades
        Map<Product, Integer> productsWithQuantities = new HashMap<>();

        // Verifica se há produtos a serem atualizados na requisição
        if (request.products() != null) {
            // Converte os produtos e suas quantidades da requisição em um mapa de produtos e suas respectivas quantidades
            productsWithQuantities = request.products().entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> productRepository.findById(entry.getKey()).orElseThrow(NoSuchElementException::new),
                            Map.Entry::getValue
                    ));

            // Verifica se a quantidade solicitada de cada produto está disponível em estoque
            for (Map.Entry<Product, Integer> entry : productsWithQuantities.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                if (product.getQuantity() < quantity) {
                    throw new OutOfStockException("The product '" + product.getName() + "' is out of stock. Current stock: " + product.getQuantity());
                }
                product.setQuantity(product.getQuantity() - quantity);
            }

            // Salva as alterações nos produtos no repositório
            productRepository.saveAll(productsWithQuantities.keySet());

            // Atualiza os produtos da ordem de serviço com os novos produtos fornecidos na requisição
            serviceOrder.setProducts(new ArrayList<>(productsWithQuantities.keySet()));
        }

        // Atualiza o preço da mão de obra, se fornecido na requisição
        if (request.laborPrice() != null) {
            serviceOrder.setLaborPrice(request.laborPrice());
        }

        // Calcula o novo custo total da ordem de serviço com base nos produtos atualizados e no preço da mão de obra
        BigDecimal cost = appUtils.costCalculator(productsWithQuantities, serviceOrder.getLaborPrice());
        serviceOrder.setCost(cost);

        // Atualiza a data de atualização da ordem de serviço
        serviceOrder.setUpdatedAt(LocalDateTime.now());

        // Salva as alterações da ordem de serviço no repositório
        serviceOrderRepository.save(serviceOrder);

        // Retorna um objeto de resposta contendo os detalhes atualizados da ordem de serviço e os produtos com suas quantidades
        return new ServiceOrderDetailsResponse(serviceOrder, productsWithQuantities);
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
