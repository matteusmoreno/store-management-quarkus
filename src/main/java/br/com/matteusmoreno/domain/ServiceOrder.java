package br.com.matteusmoreno.domain;

import br.com.matteusmoreno.constant.ServiceOrderStatus;
import br.com.matteusmoreno.request.CreateServiceOrderRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "service_orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ServiceOrder {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "service_order_status")
    private ServiceOrderStatus serviceOrderStatus;
    @ManyToMany
    @JoinTable(
            name = "service_order_products",
            joinColumns = @JoinColumn(name = "service_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
    @Column(name = "labor_price")
    private BigDecimal laborPrice;
    private BigDecimal cost;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public ServiceOrder(CreateServiceOrderRequest request) {
        this.description = request.description();
        this.serviceOrderStatus = ServiceOrderStatus.PENDING;
        this.laborPrice = request.laborPrice();
        this.createdAt = LocalDateTime.now();
    }
}
