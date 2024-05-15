package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Product findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Product> findAllById(List<Long> ids) {
        return find("id in (?1)", ids).list();
    }
}
