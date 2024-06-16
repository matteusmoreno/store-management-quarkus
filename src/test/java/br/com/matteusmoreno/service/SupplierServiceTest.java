package br.com.matteusmoreno.service;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.mapper.AddressMapper;
import br.com.matteusmoreno.supplier.Supplier;
import br.com.matteusmoreno.supplier.SupplierRepository;
import br.com.matteusmoreno.supplier.SupplierService;
import br.com.matteusmoreno.supplier.supplier_request.CreateSupplierRequest;
import br.com.matteusmoreno.supplier.supplier_request.UpdateSupplierRequest;
import br.com.matteusmoreno.utils.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    SupplierRepository supplierRepository;

    @Mock
    AppUtils appUtils;

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    SupplierService supplierService;

    UUID uuid;
    Supplier supplier;
    Address address;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        uuid = UUID.randomUUID();
        address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        supplier = new Supplier(uuid, "CORPORATE NAME", "Trade Name", "24839175000155", "(11)111111111", "ATIVA",
                "email@email.com", "Empresário (Individual)", address, LocalDateTime.now(), null, null, true);
    }

    /*
    @Test
    @DisplayName("Should create a supplier and save it to the repository")
    void shouldCreateSupplierAndSaveToRepository() {
        CreateSupplierRequest request = new CreateSupplierRequest("24839175000155");

        when(appUtils.setSupplierAttributes(request.cnpj())).thenReturn(supplier);

        Supplier result = supplierService.createSupplier(request);
        result.setId(uuid);

        verify(appUtils, times(1)).setSupplierAttributes(request.cnpj());
        verify(supplierRepository, times(1)).save(result);

        assertEquals(uuid, result.getId());
        assertEquals("CORPORATE NAME", result.getCorporateName());
        assertEquals("Trade Name", result.getTradeName());
        assertEquals(request.cnpj(), result.getCnpj());
        assertEquals("(11)111111111", result.getPhone());
        assertEquals("ATIVA", result.getRegistrationStatus());
        assertEquals("email@email.com", result.getEmail());
        assertEquals("Empresário (Individual)", result.getLegalNature());
        assertEquals(address, result.getAddress());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }
    */
    @Test
    @DisplayName("Should return supplier details by ID")
    void shouldReturnSupplierDetailsById() {

        when(supplierRepository.findById(uuid)).thenReturn(Optional.of(supplier));

        Optional<Supplier> result = Optional.ofNullable(supplierService.supplierDetails(uuid));

        verify(supplierRepository, times(1)).findById(uuid);

        assertTrue(result.isPresent());
        assertEquals(supplier, result.get());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when supplier ID not found")
    void shouldThrowNoSuchElementExceptionWhenSupplierIdNotFound() {
        when(supplierRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            supplierService.supplierDetails(uuid);
        });

        verify(supplierRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Should update supplier and save it to the repository")
    void shouldUpdateSupplierAndSaveToRepository() {
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");
        UpdateSupplierRequest request = new UpdateSupplierRequest(uuid, "New Trade Name", "(77)777777777",
                "newemail@email.com", "28994-666");

        when(supplierRepository.findById(request.id())).thenReturn(Optional.ofNullable(supplier));
        when(addressMapper.toEntity(request.zipcode())).thenReturn(newAddress);

        Supplier result = supplierService.updateSupplier(request);

        verify(supplierRepository, times(1)).findById(uuid);
        verify(supplierRepository, times(1)).save(result);
        verify(addressMapper, times(1)).toEntity(request.zipcode());

        assertEquals(request.id(), result.getId());
        assertEquals(request.tradeName(), result.getTradeName());
        assertEquals(request.phone(), result.getPhone());
        assertEquals(request.email(), result.getEmail());
        assertEquals(newAddress, result.getAddress());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when updating a supplier that does not exist")
    void shouldThrowNoSuchElementExceptionWhenUpdatingNonExistentSupplier() {
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");
        UpdateSupplierRequest request = new UpdateSupplierRequest(supplier.getId(), "New Name", "(22)996587456",
                "newemail@email.com", newAddress.getZipcode());

        when(supplierRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            supplierService.updateSupplier(request);
        });

        verify(supplierRepository, times(1)).findById(request.id());
        verify(supplierRepository, times(0)).save(any(Supplier.class));
        verify(appUtils, times(0)).ageCalculator(any(LocalDate.class));
        verify(addressMapper, times(0)).toEntity(anyString());
    }

    @Test
    @DisplayName("Should disable a supplier and mark them as deleted")
    void shouldDisableSupplierAndMarkAsDeleted() {
        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));

        supplierService.disableSupplier(supplier.getId());

        verify(supplierRepository, times(1)).save(supplier);
        assertFalse(supplier.getActive());
        assertNotNull(supplier.getDeletedAt());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when disabling a supplier that does not exist")
    void shouldThrowNoSuchElementExceptionWhenDisablingNonExistentSupplier() {
        when(supplierRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            supplierService.disableSupplier(uuid);
        });

        verify(supplierRepository, times(1)).findById(uuid);
        verify(supplierRepository, times(0)).save(any(Supplier.class));
    }

    @Test
    @DisplayName("Should enable a supplier and mark them as updated")
    void shouldEnableSupplierAndMarkAsUpdated() {
        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.enableSupplier(supplier.getId());

        verify(supplierRepository, times(1)).findById(uuid);
        verify(supplierRepository, times(1)).save(result);
        assertTrue(result.getActive());
        assertNull(result.getDeletedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when enabling a supplier that does not exist")
    void shouldThrowNoSuchElementExceptionWhenEnablingNonExistentSupplier() {
        when(supplierRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            supplierService.enableSupplier(uuid);
        });

        verify(supplierRepository, times(1)).findById(uuid);
        verify(supplierRepository, times(0)).save(any(Supplier.class));
    }
}