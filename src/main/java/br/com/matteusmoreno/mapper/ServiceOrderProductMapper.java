package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.product.Product;
import br.com.matteusmoreno.product.ProductRepository;
import br.com.matteusmoreno.service_order_product.ServiceOrderProduct;
import br.com.matteusmoreno.service_order_product.service_order_product_request.CreateServiceOrderProductRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Slf4j
@ApplicationScoped
public class ServiceOrderProductMapper {

    private final ProductRepository productRepository;

    @Inject
    public ServiceOrderProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ServiceOrderProduct toEntity(CreateServiceOrderProductRequest request) {
        log.info("Mapping service order product");

        Product product = productRepository.findById(request.productId()).orElseThrow(NoSuchElementException::new);
        product.setQuantity(product.getQuantity() - request.quantity());
        productRepository.save(product);

        return ServiceOrderProduct.builder()
                .product(product)
                .quantity(request.quantity())
                .unitaryPrice(product.getSalePrice())
                .finalPrice(product.getSalePrice().multiply(BigDecimal.valueOf(request.quantity())))
                .build();
    }
}
