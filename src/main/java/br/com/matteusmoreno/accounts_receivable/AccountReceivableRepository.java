package br.com.matteusmoreno.accounts_receivable;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class AccountReceivableRepository implements PanacheMongoRepository<AccountReceivable> {
    public AccountReceivable findByServiceOrderId(ObjectId serviceOrderId) {
        return find("serviceOrder._id", serviceOrderId).firstResult();
    }
}
