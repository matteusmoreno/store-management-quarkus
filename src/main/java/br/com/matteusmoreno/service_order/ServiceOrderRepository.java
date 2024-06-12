package br.com.matteusmoreno.service_order;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceOrderRepository implements PanacheMongoRepository<ServiceOrder> {
}
