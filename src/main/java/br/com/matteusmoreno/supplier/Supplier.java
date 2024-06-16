package br.com.matteusmoreno.supplier;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.client.brasil_api.BrasilApiResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "suppliers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Supplier {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "corporate_name")
    private String corporateName;
    @Column(name = "trade_name")
    private String tradeName;
    private String cnpj;
    private String phone;
    @Column(name = "registration_status")
    private String registrationStatus;
    private String email;
    @Column(name = "legal_nature")
    private String legalNature;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    private Boolean active;
}
