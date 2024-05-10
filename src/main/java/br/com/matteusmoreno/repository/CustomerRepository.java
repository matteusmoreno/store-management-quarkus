package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Customer findByUUID(UUID id) {
        return find("id", id).firstResult();
    }
}
