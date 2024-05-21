package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Supplier;
import br.com.matteusmoreno.repository.SupplierRepository;
import br.com.matteusmoreno.request.CreateSupplierRequest;
import br.com.matteusmoreno.request.UpdateSupplierRequest;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final AppUtils appUtils;

    @Inject
    public SupplierService(SupplierRepository supplierRepository, AppUtils appUtils) {
        this.supplierRepository = supplierRepository;
        this.appUtils = appUtils;
    }

    @Transactional
    public Supplier createSupplier(CreateSupplierRequest request) {
        Supplier supplier = appUtils.setSupplierAttributes(request.cnpj());

        supplierRepository.save(supplier);

        return supplier;
    }

    public Supplier supplierDetails(UUID id) {
        return supplierRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Supplier updateSupplier(UpdateSupplierRequest request) {
        Supplier supplier = supplierRepository.findById(request.id()).orElseThrow();

        if (request.tradeName() != null) {
            supplier.setTradeName(request.tradeName());
        }
        if (request.phone() != null) {
            supplier.setPhone(request.phone());
        }
        if (request.email() != null) {
            supplier.setEmail(request.email());
        }
        if (request.zipcode() != null) {
            supplier.setAddress(appUtils.setAddressAttributes(request.zipcode()));
        }

        supplier.setUpdatedAt(LocalDateTime.now());
        supplierRepository.save(supplier);

        return supplier;
    }

    @Transactional
    public void disableSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplier.setActive(false);
        supplier.setDeletedAt(LocalDateTime.now());

        supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier enableSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplier.setActive(true);
        supplier.setDeletedAt(null);
        supplier.setUpdatedAt(LocalDateTime.now());

        supplierRepository.save(supplier);

        return supplier;
    }
}
