package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
