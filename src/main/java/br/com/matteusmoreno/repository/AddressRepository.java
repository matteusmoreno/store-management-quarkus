package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByZipcode(String zipcode);

    Address findByZipcode(String zipcode);
}
