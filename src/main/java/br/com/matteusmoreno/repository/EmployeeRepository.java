package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    public Employee findByUUID(UUID id) {
        return find("id", id).firstResult();
    }

    public Employee findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
