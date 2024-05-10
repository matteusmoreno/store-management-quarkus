package br.com.matteusmoreno.domain;

import br.com.matteusmoreno.constant.Role;
import br.com.matteusmoreno.request.CreateEmployeeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private Integer age;
    private String phone;
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private Role role;
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

    public Employee(CreateEmployeeRequest request) {
        this.name = request.name();
        this.birthDate = request.birthDate();
        this.phone = request.phone();
        this.salary = request.salary();
        this.role = request.role();
        this.email = request.email();
        this.cpf = request.cpf();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
