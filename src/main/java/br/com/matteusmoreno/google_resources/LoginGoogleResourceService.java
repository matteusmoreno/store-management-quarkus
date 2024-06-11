package br.com.matteusmoreno.google_resources;

import br.com.matteusmoreno.customer.CustomerRepository;
import br.com.matteusmoreno.employee.EmployeeRepository;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;

@ApplicationScoped
public class LoginGoogleResourceService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final SecurityIdentity securityIdentity;

    @Inject
    public LoginGoogleResourceService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, SecurityIdentity securityIdentity) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.securityIdentity = securityIdentity;
    }

    public String getJwtToken() {
        // Obter as informações do usuário autenticado
        JsonWebToken jwt = (JsonWebToken) securityIdentity.getPrincipal();

        String givenName = jwt.getClaim("given_name");
        String email = jwt.getClaim("email");

        boolean isCustomer = customerRepository.existsByEmail(email);
        boolean isEmployee = employeeRepository.existsByEmail(email);

        if (isCustomer) {
            return Jwt.issuer("https:store-management.com")
                    .upn(email)
                    .subject(givenName)
                    .groups("customer")
                    .expiresIn(Duration.ofHours(1))
                    .sign();
        } else if (isEmployee) {
            return Jwt.issuer("https:store-management.com")
                    .upn(email)
                    .subject(givenName)
                    .groups("employee")
                    .expiresIn(Duration.ofHours(1))
                    .sign();
        } else {
            throw new NotFoundException("User not found");
        }

    }
}
