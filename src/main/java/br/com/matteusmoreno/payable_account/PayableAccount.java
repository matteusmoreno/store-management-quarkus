package br.com.matteusmoreno.payable_account;

import br.com.matteusmoreno.accounts_receivable.TransactionStatus;
import br.com.matteusmoreno.supplier.Supplier;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDate;

@MongoEntity(database = "store_management", collection = "payable_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class PayableAccount {

    private ObjectId id;
    private Supplier supplier;
    private String description;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private TransactionStatus transactionStatus;
}
