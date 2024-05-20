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

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a customer and save it to the repository")
    void shouldCreateCustomerAndSaveToRepository() {
        UUID uuid = UUID.randomUUID();
        CreateCustomerRequest request = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "(22)998223307", "222.222.222-22", "22222-666");
        Address address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        Integer age = 30;

        when(appUtils.setAddressAttributes(any())).thenReturn(address);
        when(appUtils.ageCalculator(any())).thenReturn(age);

        Customer customer = customerService.createCustomer(request);
        customer.setId(uuid);

        verify(appUtils, times(1)).setAddressAttributes(any());
        verify(appUtils, times(1)).ageCalculator(any());
        verify(customerRepository, times(1)).save(customer);
        assertEquals(uuid, customer.getId());
        assertEquals("Name", customer.getName());
        assertEquals(LocalDate.of(1990, 8, 28), customer.getBirthDate());
        assertEquals(age, customer.getAge());
        assertEquals("matteus@email.com", customer.getEmail());
        assertEquals("(22)998223307", customer.getPhone());
        assertEquals("222.222.222-22", customer.getCpf());
        assertEquals(address, customer.getAddress());

        assertEquals(LocalDate.now(), customer.getCreatedAt().toLocalDate());
        assertEquals(LocalDateTime.now().getHour(), customer.getCreatedAt().getHour());
        assertEquals(LocalDateTime.now().getMinute(), customer.getCreatedAt().getMinute());
        assertEquals(LocalDateTime.now().getSecond(), customer.getCreatedAt().getSecond());
        assertNull(customer.getUpdatedAt());

        assertNull(customer.getDeletedAt());
        assertTrue(customer.getActive());
    }

    @Test
    @DisplayName("Should return customer details by ID")
    void shouldReturnCustomerDetailsById() {
        UUID uuid = UUID.randomUUID();
        Address address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        Customer customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 34, "(22)222222222",
                "email@email.com", "222.222.222-22", address, LocalDateTime.now(), null, null, true);

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
        UUID uuid = UUID.randomUUID();
        Address address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        Customer customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 34, "(22)222222222",
                "email@email.com", "222.222.222-22", address, LocalDateTime.now(), null, null, true);

        List<Customer> customers = Collections.singletonList(customer);

        when(customerRepository.findByAddressNeighborhoodContainingIgnoreCase(anyString())).thenReturn(customers);

        List<Customer> result = customerService.customerDetailsByNeighborhood(neighborhood);

        verify(customerRepository, times(1)).findByAddressNeighborhoodContainingIgnoreCase(neighborhood);
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    @DisplayName("Should create a update and save it to the repository")
    void shouldUpdateCustomerAndSaveToRepository() {
        UUID uuid = UUID.randomUUID();
        Address oldAddress = new Address(1L, "28994-666", "St. A", "City A", "Neighborhood A", "RH");

        Customer customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 33, "(22)222222222",
                "email@email.com", "222.222.222-22", oldAddress, LocalDateTime.now(), null, null, true);

        LocalDate newBirthDate = LocalDate.of(1989, 8, 28);
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");

        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getId(), "New Name", newBirthDate, "newemail@email.com",
                "(33)333333333", "000.000.000-00", newAddress.getZipcode());

        when(customerRepository.findById(uuid)).thenReturn(Optional.of(customer));
        when(appUtils.setAddressAttributes(newAddress.getZipcode())).thenReturn(newAddress);
        when(appUtils.ageCalculator(request.birthDate())).thenReturn(34);

        Customer updatedCustomer = customerService.updateCustomer(request);

        verify(customerRepository, times(1)).findById(uuid);
        verify(appUtils, times(1)).ageCalculator(request.birthDate());
        verify(appUtils, times(1)).setAddressAttributes(request.zipcode());

        assertEquals("New Name", updatedCustomer.getName());
        assertEquals(newBirthDate, updatedCustomer.getBirthDate());
        assertEquals(34, updatedCustomer.getAge());
        assertEquals("(33)333333333", updatedCustomer.getPhone());
        assertEquals("newemail@email.com", updatedCustomer.getEmail());
        assertEquals("000.000.000-00", updatedCustomer.getCpf());
        assertEquals(newAddress, updatedCustomer.getAddress());
        assertNotNull(updatedCustomer.getCreatedAt());
        assertNotNull(updatedCustomer.getUpdatedAt());
        assertNull(updatedCustomer.getDeletedAt());
        assertTrue(updatedCustomer.getActive());
    }

    @Test
    @DisplayName("Should disable a customer and mark them as deleted")
    void shouldDisableCustomerAndMarkAsDeleted() {
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 34, "(22)222222222",
                "email@email.com", "222.222.222-22", new Address(), LocalDateTime.now(), null, null, true);
        customer.setId(uuid);

        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        customerService.disableCustomer(customer.getId());

        verify(customerRepository, times(1)).save(customer);
        assertFalse(customer.getActive());
        assertNotNull(customer.getDeletedAt());
    }

    @Test
    @DisplayName("Should enable a customer and mark them as updated")
    void shouldEnableCustomerAndMarkAsUpdated() {
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 34, "(22)222222222",
                "email@email.com", "222.222.222-22", new Address(), LocalDateTime.now(), null, null, true);
        customer.setId(uuid);

        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        customerService.enableCustomer(customer.getId());

        verify(customerRepository, times(1)).save(customer);
        assertTrue(customer.getActive());
        assertNull(customer.getDeletedAt());
        assertNotNull(customer.getUpdatedAt());
    }
}