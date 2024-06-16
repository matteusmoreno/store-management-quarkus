package br.com.matteusmoreno.service_order_product;

import br.com.matteusmoreno.mapper.ServiceOrderProductMapper;
import br.com.matteusmoreno.service_order_product.service_order_product_request.CreateServiceOrderProductRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServiceOrderProductService {

    private final ServiceOrderProductRepository serviceOrderProductRepository;
    private final ServiceOrderProductMapper serviceOrderProductMapper;

    @Inject
    public ServiceOrderProductService(ServiceOrderProductRepository serviceOrderProductRepository, ServiceOrderProductMapper serviceOrderProductMapper) {
        this.serviceOrderProductRepository = serviceOrderProductRepository;
        this.serviceOrderProductMapper = serviceOrderProductMapper;
    }

    public List<ServiceOrderProduct> createServiceOrderProduct(List<CreateServiceOrderProductRequest> request) {

        List<ServiceOrderProduct> serviceOrderProductList = new ArrayList<>();

        request.forEach(serviceOrderProductRequest -> {
            ServiceOrderProduct serviceOrderProduct = serviceOrderProductMapper.toEntity(serviceOrderProductRequest);

            serviceOrderProductRepository.persist(serviceOrderProduct);
            serviceOrderProductList.add(serviceOrderProduct);
        });

        return serviceOrderProductList;
    }
}
