package br.com.matteusmoreno.service_order_product;

import br.com.matteusmoreno.exception.exception_class.OutOfStockException;
import br.com.matteusmoreno.product.Product;
import br.com.matteusmoreno.product.ProductRepository;
import br.com.matteusmoreno.service_order_product.service_order_product_request.CreateServiceOrderProductRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
public class ServiceOrderProductService {

    private final ServiceOrderProductRepository serviceOrderProductRepository;
    private final ProductRepository productRepository;

    @Inject
    public ServiceOrderProductService(ServiceOrderProductRepository serviceOrderProductRepository, ProductRepository productRepository) {
        this.serviceOrderProductRepository = serviceOrderProductRepository;
        this.productRepository = productRepository;
    }

    public List<ServiceOrderProduct> createServiceOrderProduct(List<CreateServiceOrderProductRequest> request) {

        List<ServiceOrderProduct> serviceOrderProductList = new ArrayList<>();

        request.forEach(serviceOrderProductRequest -> {
            Product product = productRepository.findById(serviceOrderProductRequest.productId()).orElseThrow(NoSuchElementException::new);

            if (serviceOrderProductRequest.quantity() > product.getQuantity()) {
                throw new OutOfStockException("Product Out of Stock");
            }

            Integer serviceOrderQuantity = serviceOrderProductRequest.quantity();

            ServiceOrderProduct serviceOrderProduct = new ServiceOrderProduct();
            serviceOrderProduct.setProduct(product);
            serviceOrderProduct.setQuantity(serviceOrderQuantity);
            serviceOrderProduct.setUnitaryPrice(product.getSalePrice());
            serviceOrderProduct.setFinalPrice(product.getSalePrice().multiply(BigDecimal.valueOf(serviceOrderQuantity)));

            product.setQuantity(product.getQuantity() - serviceOrderQuantity);
            productRepository.save(product);

            serviceOrderProductRepository.persist(serviceOrderProduct);
            serviceOrderProductList.add(serviceOrderProduct);
        });

        return serviceOrderProductList;
    }
}
