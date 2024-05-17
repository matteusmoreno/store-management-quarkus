package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

}
