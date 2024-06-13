package br.com.matteusmoreno.accounts_receivable;

import br.com.matteusmoreno.service_order.ServiceOrder;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@MongoEntity(database = "store_management", collection = "accounts_receivable")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AccountReceivable {

    private ObjectId id;
    private ServiceOrder serviceOrder;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;

    public AccountReceivable(ServiceOrder serviceOrder, BigDecimal amount) {
        this.amount = amount;
        this.serviceOrder = serviceOrder;
        this.transactionStatus = TransactionStatus.PENDING;
    }
}
