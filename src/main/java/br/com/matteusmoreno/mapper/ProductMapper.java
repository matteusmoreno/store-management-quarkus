package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.product.Product;
import br.com.matteusmoreno.product.ProductRepository;
import br.com.matteusmoreno.product.product_request.CreateProductRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class ProductMapper {

    private final ProductRepository productRepository;

    @Inject
    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product toEntity(CreateProductRequest request) {
        log.info("Mapping product to entity");

        if (productRepository.existsByNameIgnoreCaseAndManufacturerIgnoreCase(request.name().toUpperCase(), request.manufacturer().toUpperCase())) {
            Product product = productRepository.findByNameIgnoreCaseAndManufacturerIgnoreCase(request.name().toUpperCase(), request.manufacturer().toUpperCase());
            product.setQuantity(product.getQuantity() + request.quantity());
            productRepository.save(product);
            return product;
        }

        return Product.builder()
                .name(request.name().toUpperCase())
                .description(request.description().toUpperCase())
                .purchasePrice(request.purchasePrice())
                .salePrice(request.salePrice())
                .manufacturer(request.manufacturer().toUpperCase())
                .quantity(request.quantity())
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();
    }
}
