package br.com.matteusmoreno.customer;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.customer.customer_request.CreateCustomerRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Customer extends PanacheEntityBase {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private Integer age;
    private String phone;
    private String email;
    private String cpf;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    private Boolean active;


    public Customer(CreateCustomerRequest request) {
        this.name = request.name();
        this.birthDate = request.birthDate();
        this.phone = request.phone();
        this.email = request.email();
        this.cpf = request.cpf();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}


