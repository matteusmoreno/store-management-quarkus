package br.com.matteusmoreno.accounts_receivable;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountReceivableRepository implements PanacheMongoRepository<AccountReceivable> {
}
