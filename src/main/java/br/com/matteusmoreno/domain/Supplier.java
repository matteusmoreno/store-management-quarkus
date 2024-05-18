package br.com.matteusmoreno.domain;

import br.com.matteusmoreno.client.brasil_api.BrasilApiResponse;
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

    public Supplier(BrasilApiResponse response) {
        this.corporateName = response.razao_social();
        this.tradeName = response.nome_fantasia();
        this.phone = response.ddd_telefone_1();
        this.registrationStatus = response.descricao_situacao_cadastral();
        this.email = response.email();
        this.legalNature = response.natureza_juridica();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
