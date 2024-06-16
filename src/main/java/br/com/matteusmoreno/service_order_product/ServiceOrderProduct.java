package br.com.matteusmoreno.service_order_product;

import br.com.matteusmoreno.product.Product;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@MongoEntity(database = "store_management", collection = "service_orders_products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class ServiceOrderProduct {

    private ObjectId id;
    private Product product;
    private Integer quantity;
    private BigDecimal unitaryPrice;
    private BigDecimal finalPrice;
}
