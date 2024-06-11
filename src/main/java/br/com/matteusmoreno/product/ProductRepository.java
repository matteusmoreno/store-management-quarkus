package br.com.matteusmoreno.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCaseAndManufacturerIgnoreCase(String name, String manufacturer);

    Product findByNameIgnoreCaseAndManufacturerIgnoreCase(String name, String manufacturer);

}
