package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.accounts_receivable.AccountReceivable;
import br.com.matteusmoreno.accounts_receivable.TransactionStatus;
import br.com.matteusmoreno.service_order.ServiceOrder;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AccountReceivableMapper {

    public AccountReceivable toEntity(ServiceOrder serviceOrder) {
        log.info("Mapping Account Receivable");

        return AccountReceivable.builder()
                .serviceOrder(serviceOrder)
                .amount(serviceOrder.getTotalCost())
                .transactionStatus(TransactionStatus.PENDING)
                .build();
    }
}
