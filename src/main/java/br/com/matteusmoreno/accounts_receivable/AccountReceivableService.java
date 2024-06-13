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

    public void payAccountReceivable(ServiceOrder serviceOrder) {
        AccountReceivable accountReceivable = accountReceivableRepository.findByServiceOrderId(serviceOrder.getId());

        accountReceivable.setTransactionStatus(TransactionStatus.PAID);
        accountReceivable.setServiceOrder(serviceOrder);

        accountReceivableRepository.update(accountReceivable);
    }

    public void cancelAccountReceivable(ServiceOrder serviceOrder) {
        AccountReceivable accountReceivable = accountReceivableRepository.findByServiceOrderId(serviceOrder.getId());

        accountReceivable.setTransactionStatus(TransactionStatus.CANCELED);
        accountReceivable.setServiceOrder(serviceOrder);

        accountReceivableRepository.update(accountReceivable);
    }
}
