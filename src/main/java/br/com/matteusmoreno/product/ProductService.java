package br.com.matteusmoreno.product;

import br.com.matteusmoreno.mapper.ProductMapper;
import br.com.matteusmoreno.product.product_request.CreateProductRequest;
import br.com.matteusmoreno.product.product_request.UpdateProductRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ApplicationScoped
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Inject
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public Product createProduct(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);

        productRepository.save(product);

        return product;
    }

    public Product productDetails(Long id) {
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Product updateProduct(UpdateProductRequest request) {
        Product product = productRepository.findById(request.id()).orElseThrow(NoSuchElementException::new);

        if (request.name() != null) {
            product.setName(request.name().toUpperCase());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.purchasePrice() != null) {
            product.setPurchasePrice(request.purchasePrice());
        }
        if (request.salePrice() != null) {
            product.setSalePrice(request.salePrice());
        }
        if (request.manufacturer() != null) {
            product.setManufacturer(request.manufacturer().toUpperCase());
        }
        if (request.quantity() != null) {
            product.setQuantity(request.quantity());
        }

        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return product;
    }

    @Transactional
    public void disableProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
        product.setActive(false);
        product.setDeletedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    @Transactional
    public Product enableProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
        product.setActive(true);
        product.setDeletedAt(null);
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        return product;
    }
}
