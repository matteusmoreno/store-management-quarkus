package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Employee;
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

    @Inject
    SupplierRepository supplierRepository;

    @Inject
    AppUtils appUtils;

    @Transactional
    public Supplier createSupplier(CreateSupplierRequest request) {
        Address address = appUtils.setAddressAttributes(request.zipcode());

        Supplier supplier = new Supplier(request);
        supplier.setAddress(address);

        supplierRepository.persist(supplier);

        return supplier;
    }

    public Supplier supplierDetails(UUID id) {
        return supplierRepository.findByUUID(id);
    }

    @Transactional
    public Supplier updateSupplier(UpdateSupplierRequest request) {
        Supplier supplier = supplierRepository.findByUUID(request.id());

        if (request.name() != null) {
            supplier.setName(request.name());
        }
        if (request.cnpj() != null) {
            supplier.setCnpj(request.cnpj());
        }
        if (request.phone() != null) {
            supplier.setPhone(request.phone());
        }
        if (request.email() != null) {
            supplier.setEmail(request.email());
        }
        if (request.site() != null) {
            supplier.setSite(request.site());
        }
        if (request.zipcode() != null) {
            supplier.setAddress(appUtils.setAddressAttributes(request.zipcode()));
        }

        supplier.setUpdatedAt(LocalDateTime.now());
        supplierRepository.persist(supplier);

        return supplier;
    }

    @Transactional
    public void disableSupplier(UUID id) {
        Supplier supplier = supplierRepository.findByUUID(id);
        supplier.setActive(false);
        supplier.setDeletedAt(LocalDateTime.now());

        supplierRepository.persist(supplier);
    }

    @Transactional
    public Supplier enableSupplier(UUID id) {
        Supplier supplier = supplierRepository.findByUUID(id);
        supplier.setActive(true);
        supplier.setDeletedAt(null);
        supplier.setUpdatedAt(LocalDateTime.now());

        return supplier;
    }
}
