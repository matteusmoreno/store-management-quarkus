package br.com.matteusmoreno.payable_account;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PayableAccountRepository implements PanacheMongoRepository<PayableAccount> {
}
