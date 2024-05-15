package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.repository.ProductRepository;
import br.com.matteusmoreno.request.CreateProductRequest;
import br.com.matteusmoreno.request.UpdateProductRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Product createProduct(CreateProductRequest request) {
        Product product = new Product(request);

        productRepository.persist(product);

        return product;
    }

    public Product productDetails(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product updateProduct(UpdateProductRequest request) {
        Product product = productRepository.findById(request.id());

        if (request.name() != null) {
            product.setName(request.name());
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
            product.setManufacturer(request.manufacturer());
        }

        product.setUpdatedAt(LocalDateTime.now());
        productRepository.persist(product);

        return product;
    }

    @Transactional
    public void disableProduct(Long id) {
        Product product = productRepository.findById(id);
        product.setActive(false);
        product.setDeletedAt(LocalDateTime.now());

        productRepository.persist(product);
    }

    @Transactional
    public Product enableProduct(Long id) {
        Product product = productRepository.findById(id);
        product.setActive(true);
        product.setDeletedAt(null);
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }
}