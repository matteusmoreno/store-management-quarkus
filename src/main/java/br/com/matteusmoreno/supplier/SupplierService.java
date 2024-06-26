package br.com.matteusmoreno.supplier;

import br.com.matteusmoreno.mapper.AddressMapper;
import br.com.matteusmoreno.mapper.SupplierMapper;
import br.com.matteusmoreno.supplier.supplier_request.CreateSupplierRequest;
import br.com.matteusmoreno.supplier.supplier_request.UpdateSupplierRequest;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final AddressMapper addressMapper;

    @Inject
    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper, AddressMapper addressMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public Supplier createSupplier(CreateSupplierRequest request) {
        Supplier supplier = supplierMapper.toEntity(request);

        supplierRepository.save(supplier);

        return supplier;
    }

    public Supplier supplierDetails(UUID id) {
        return supplierRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Supplier updateSupplier(UpdateSupplierRequest request) {
        Supplier supplier = supplierRepository.findById(request.id()).orElseThrow(NoSuchElementException::new);

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
            supplier.setAddress(addressMapper.toEntity(request.zipcode()));
        }

        supplier.setUpdatedAt(LocalDateTime.now());
        supplierRepository.save(supplier);

        return supplier;
    }

    @Transactional
    public void disableSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(NoSuchElementException::new);
        supplier.setActive(false);
        supplier.setDeletedAt(LocalDateTime.now());

        supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier enableSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(NoSuchElementException::new);
        supplier.setActive(true);
        supplier.setDeletedAt(null);
        supplier.setUpdatedAt(LocalDateTime.now());

        supplierRepository.save(supplier);

        return supplier;
    }
}
