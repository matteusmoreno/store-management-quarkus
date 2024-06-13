package br.com.matteusmoreno.service_order;

import br.com.matteusmoreno.customer.Customer;
import br.com.matteusmoreno.employee.Employee;
import br.com.matteusmoreno.service_order.constant.ServiceOrderStatus;
import br.com.matteusmoreno.service_order_product.ServiceOrderProduct;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MongoEntity(database = "store_management", collection = "service_orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ServiceOrder {

    private ObjectId id;
    private Customer customer;
    private Employee employee;
    private List<ServiceOrderProduct> serviceOrderProducts = new ArrayList<>();
    private BigDecimal laborPrice;
    private BigDecimal totalCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ServiceOrderStatus serviceOrderStatus;
}
