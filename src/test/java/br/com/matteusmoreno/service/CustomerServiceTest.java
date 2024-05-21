package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.request.CreateCustomerRequest;
import br.com.matteusmoreno.request.UpdateCustomerRequest;
import br.com.matteusmoreno.utils.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AppUtils appUtils;

    @InjectMocks
    CustomerService customerService;

    private UUID uuid;
    private Address address;
    private Customer customer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        uuid = UUID.randomUUID();
        address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 34, "(22)222222222",
                "email@email.com", "222.222.222-22", address, LocalDateTime.now(), null, null, true);
    }

    @Test
    @DisplayName("Should create a customer and save it to the repository")
    void shouldCreateCustomerAndSaveToRepository() {
        CreateCustomerRequest request = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "(22)998223307", "222.222.222-22", "22222-666");

        when(appUtils.setAddressAttributes(request.zipcode())).thenReturn(address);
        when(appUtils.ageCalculator(any())).thenReturn(34);

        Customer result = customerService.createCustomer(request);
        result.setId(uuid);

        verify(appUtils, times(1)).setAddressAttributes(any());
        verify(appUtils, times(1)).ageCalculator(any());
        verify(customerRepository, times(1)).save(result);

        assertEquals(uuid, result.getId());
        assertEquals("Name", result.getName());
        assertEquals(LocalDate.of(1990, 8, 28), result.getBirthDate());
        assertEquals(34, result.getAge());
        assertEquals("matteus@email.com", result.getEmail());
        assertEquals("(22)998223307", result.getPhone());
        assertEquals("222.222.222-22", result.getCpf());
        assertEquals(address, result.getAddress());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should return customer details by ID")
    void shouldReturnCustomerDetailsById() {
        when(customerRepository.findById(uuid)).thenReturn(Optional.of(customer));

        Optional<Customer> result = Optional.ofNullable(customerService.customerDetailsById(uuid));

        verify(customerRepository, times(1)).findById(uuid);
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    @DisplayName("Should return customer details by Neighborhood")
    void shouldReturnCustomerDetailsByNeighborhood() {
        String neighborhood = "Neighborhood";
        List<Customer> customers = Collections.singletonList(customer);

        when(customerRepository.findByAddressNeighborhoodContainingIgnoreCase(anyString())).thenReturn(customers);

        List<Customer> result = customerService.customerDetailsByNeighborhood(neighborhood);

        verify(customerRepository, times(1)).findByAddressNeighborhoodContainingIgnoreCase(neighborhood);
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    @DisplayName("Should update customer and save it to the repository")
    void shouldUpdateCustomerAndSaveToRepository() {
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getId(), "New Name", LocalDate.of(1989, 8, 28), "newemail@email.com",
                "(33)333333333", "000.000.000-00", newAddress.getZipcode());

        when(customerRepository.findById(request.id())).thenReturn(Optional.of(customer));
        when(appUtils.setAddressAttributes(newAddress.getZipcode())).thenReturn(newAddress);
        when(appUtils.ageCalculator(request.birthDate())).thenReturn(34);

        Customer result = customerService.updateCustomer(request);

        verify(customerRepository, times(1)).findById(uuid);
        verify(customerRepository, times(1)).save(result);
        verify(appUtils, times(1)).ageCalculator(request.birthDate());
        verify(appUtils, times(1)).setAddressAttributes(request.zipcode());

        assertEquals(request.id(), result.getId());
        assertEquals(request.name(), result.getName());
        assertEquals(request.birthDate(), result.getBirthDate());
        assertEquals(34, result.getAge());
        assertEquals(request.phone(), result.getPhone());
        assertEquals(request.email(), result.getEmail());
        assertEquals(request.cpf(), result.getCpf());
        assertEquals(newAddress, result.getAddress());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should disable a customer and mark them as deleted")
    void shouldDisableCustomerAndMarkAsDeleted() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        customerService.disableCustomer(customer.getId());

        verify(customerRepository, times(1)).save(customer);
        assertFalse(customer.getActive());
        assertNotNull(customer.getDeletedAt());
    }

    @Test
    @DisplayName("Should enable a customer and mark them as updated")
    void shouldEnableCustomerAndMarkAsUpdated() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        Customer result = customerService.enableCustomer(customer.getId());

        verify(customerRepository, times(1)).findById(uuid);
        verify(customerRepository, times(1)).save(result);
        assertTrue(result.getActive());
        assertNull(result.getDeletedAt());
        assertNotNull(result.getUpdatedAt());
    }
}