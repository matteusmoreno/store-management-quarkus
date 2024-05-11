package br.com.matteusmoreno.domain;

import br.com.matteusmoreno.request.CreateSupplierRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "suppliers")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Supplier {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String cnpj;
    private String phone;
    private String email;
    private String site;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    private Boolean active;

    public Supplier(CreateSupplierRequest request) {
        this.name = request.name();
        this.cnpj = request.cnpj();
        this.phone = request.phone();
        this.email = request.email();
        this.site = request.site();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
