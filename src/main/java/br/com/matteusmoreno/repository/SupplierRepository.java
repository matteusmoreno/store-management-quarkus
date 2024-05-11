package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.domain.Supplier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class SupplierRepository implements PanacheRepository<Supplier> {

    public Supplier findByUUID(UUID id) {
        return find("id", id).firstResult();
    }

    public Supplier findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
