package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer>findByAddressNeighborhoodContainingIgnoreCase(String neighborhood);

}
