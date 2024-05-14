package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Product findByUUID(UUID id) {
        return find("id", id).firstResult();
    }

    public Product findByName(String name) {
        return find("name", name).firstResult();
    }
}
