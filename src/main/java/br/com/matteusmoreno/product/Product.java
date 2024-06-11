package br.com.matteusmoreno.product;

import br.com.matteusmoreno.product.product_request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "sale_price")
    private BigDecimal salePrice;
    private String manufacturer;
    private Integer quantity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    private Boolean active;

    public Product(CreateProductRequest request) {
        this.name = request.name();
        this.description = request.description();
        this.purchasePrice = request.purchasePrice();
        this.salePrice = request.salePrice();
        this.manufacturer = request.manufacturer();
        this.quantity = request.quantity();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
