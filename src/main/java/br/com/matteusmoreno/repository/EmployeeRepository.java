package br.com.matteusmoreno.repository;

import br.com.matteusmoreno.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);
}
