package br.com.matteusmoreno.accounts_receivable;

import br.com.matteusmoreno.service_order.ServiceOrder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountReceivableService {

    private final AccountReceivableRepository accountReceivableRepository;

    @Inject
    public AccountReceivableService(AccountReceivableRepository accountReceivableRepository) {
        this.accountReceivableRepository = accountReceivableRepository;
    }

    public void createAccountReceivable(ServiceOrder serviceOrder) {
        AccountReceivable accountReceivable = new AccountReceivable(serviceOrder, serviceOrder.getTotalCost());

        accountReceivableRepository.persist(accountReceivable);
    }
}
