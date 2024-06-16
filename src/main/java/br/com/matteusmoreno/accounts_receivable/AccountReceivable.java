package br.com.matteusmoreno.accounts_receivable;

import br.com.matteusmoreno.service_order.ServiceOrder;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@MongoEntity(database = "store_management", collection = "accounts_receivable")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class AccountReceivable {

    private ObjectId id;
    private ServiceOrder serviceOrder;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
}
