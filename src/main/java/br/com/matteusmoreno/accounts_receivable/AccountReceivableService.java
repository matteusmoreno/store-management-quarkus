package br.com.matteusmoreno.accounts_receivable;

import br.com.matteusmoreno.mapper.AccountReceivableMapper;
import br.com.matteusmoreno.service_order.ServiceOrder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountReceivableService {

    private final AccountReceivableRepository accountReceivableRepository;
    private final AccountReceivableMapper accountReceivableMapper;

    @Inject
    public AccountReceivableService(AccountReceivableRepository accountReceivableRepository, AccountReceivableMapper accountReceivableMapper) {
        this.accountReceivableRepository = accountReceivableRepository;
        this.accountReceivableMapper = accountReceivableMapper;
    }

    public void createAccountReceivable(ServiceOrder serviceOrder) {
        AccountReceivable accountReceivable = accountReceivableMapper.toEntity(serviceOrder);

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
