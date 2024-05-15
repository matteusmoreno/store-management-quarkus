package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.ServiceOrder;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ServiceOrderRepository implements PanacheRepository<ServiceOrder> {

    public ServiceOrder findByUUID(UUID id) {
        return find("id", id).firstResult();
    }
}
