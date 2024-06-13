package br.com.matteusmoreno.payable_account.payable_account_response;

import br.com.matteusmoreno.accounts_receivable.TransactionStatus;
import br.com.matteusmoreno.payable_account.PayableAccount;
import br.com.matteusmoreno.supplier.Supplier;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayableAccountDetailsResponse(
        ObjectId id,
        Supplier supplier,
        String description,
        BigDecimal amount,
        LocalDate paymentDate,
        TransactionStatus transactionStatus) {

    public PayableAccountDetailsResponse(PayableAccount payableAccount) {
        this(payableAccount.getId(), payableAccount.getSupplier(), payableAccount.getDescription(),
                payableAccount.getAmount(), payableAccount.getPaymentDate(), payableAccount.getTransactionStatus());
    }
}
