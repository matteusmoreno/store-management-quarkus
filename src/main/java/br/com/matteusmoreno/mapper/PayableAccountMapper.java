package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.payable_account.PayableAccount;
import br.com.matteusmoreno.payable_account.payable_account_request.CreatePayableAccountRequest;
import br.com.matteusmoreno.supplier.SupplierRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

@Slf4j
@ApplicationScoped
public class PayableAccountMapper {

    private final SupplierRepository supplierRepository;

    @Inject
    public PayableAccountMapper(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public PayableAccount toEntity(CreatePayableAccountRequest request) {


        log.info("Mapping Payable Account");
        return PayableAccount.builder()
                .supplier(supplierRepository.findById(request.supplierId()).orElseThrow(NoSuchElementException::new))
                .description(request.description())
                .amount(request.amount())
                .paymentDate(request.paymentDate())
                .build();
    }
}
