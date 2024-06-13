package br.com.matteusmoreno.payable_account;

import br.com.matteusmoreno.accounts_receivable.TransactionStatus;
import br.com.matteusmoreno.payable_account.payable_account_request.CreatePayableAccountRequest;
import br.com.matteusmoreno.supplier.Supplier;
import br.com.matteusmoreno.supplier.SupplierRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PayableAccountService {

    private final PayableAccountRepository payableAccountRepository;
    private final SupplierRepository supplierRepository;

    @Inject
    public PayableAccountService(PayableAccountRepository payableAccountRepository, SupplierRepository supplierRepository) {
        this.payableAccountRepository = payableAccountRepository;
        this.supplierRepository = supplierRepository;
    }

    public PayableAccount createPayableAccount(CreatePayableAccountRequest request) {
        Supplier supplier = supplierRepository.findById(request.supplierId()).orElseThrow();

        PayableAccount payableAccount = new PayableAccount();
        payableAccount.setSupplier(supplier);
        payableAccount.setDescription(request.description());
        payableAccount.setAmount(request.amount());
        payableAccount.setPaymentDate(request.paymentDate());
        payableAccount.setTransactionStatus(TransactionStatus.PENDING);

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
