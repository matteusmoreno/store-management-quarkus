package br.com.matteusmoreno.payable_account;

import br.com.matteusmoreno.accounts_receivable.TransactionStatus;
import br.com.matteusmoreno.mapper.PayableAccountMapper;
import br.com.matteusmoreno.payable_account.payable_account_request.CreatePayableAccountRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PayableAccountService {

    private final PayableAccountRepository payableAccountRepository;
    private final PayableAccountMapper payableAccountMapper;

    @Inject
    public PayableAccountService(PayableAccountRepository payableAccountRepository, PayableAccountMapper payableAccountMapper) {
        this.payableAccountRepository = payableAccountRepository;

        this.payableAccountMapper = payableAccountMapper;
    }

    public PayableAccount createPayableAccount(CreatePayableAccountRequest request) {
        PayableAccount payableAccount = payableAccountMapper.toEntity(request);

        payableAccountRepository.persist(payableAccount);

        return payableAccount;
    }

    public PayableAccount payableAccountDetails(ObjectId id) {
        return payableAccountRepository.findById(id);
    }

    public PayableAccount payPayableAccount(ObjectId id) {
        PayableAccount payableAccount = payableAccountRepository.findById(id);

        payableAccount.setTransactionStatus(TransactionStatus.PAID);
        payableAccountRepository.update(payableAccount);

        return payableAccount;
    }

    public PayableAccount cancelPayableAccount(ObjectId id) {
        PayableAccount payableAccount = payableAccountRepository.findById(id);

        payableAccount.setTransactionStatus(TransactionStatus.CANCELED);
        payableAccountRepository.update(payableAccount);

        return payableAccount;
    }
}
