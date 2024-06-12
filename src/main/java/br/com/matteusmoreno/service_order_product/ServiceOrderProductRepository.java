package br.com.matteusmoreno.service_order_product;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceOrderProductRepository implements PanacheMongoRepository<ServiceOrderProduct> {
}
