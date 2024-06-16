package br.com.matteusmoreno.service;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.customer.Customer;
import br.com.matteusmoreno.customer.CustomerRepository;
import br.com.matteusmoreno.customer.CustomerService;
import br.com.matteusmoreno.customer.customer_request.CreateCustomerRequest;
import br.com.matteusmoreno.customer.customer_request.UpdateCustomerRequest;
import br.com.matteusmoreno.mapper.AddressMapper;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AddressMapper addressMapper;

    @Mock
    AppUtils appUtils;

    @InjectMocks
    CustomerService customerService;

    private UUID uuid;
    private Address address;
    private Customer customer;
    private Validator validator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        uuid = UUID.randomUUID();
        address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        customer = new Customer(uuid, "Name", LocalDate.of(1990, 8, 28), 34, "(22)222222222",
                "email@email.com", "222.222.222-22", address, LocalDateTime.now(), null, null, true);
    }
    /*
    @Test
    @DisplayName("Should create a customer and save it to the repository")
    void shouldCreateCustomerAndSaveToRepository() {
        CreateCustomerRequest request = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "(22)998223307", "222.222.222-22", "22222-666");

        when(addressMapper.toEntity(request.zipcode())).thenReturn(address);
        when(appUtils.ageCalculator(any())).thenReturn(34);

        Customer result = customerService.createCustomer(request);
        result.setId(uuid);

        verify(addressMapper, times(1)).toEntity(any());
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
    */
    @Test
    @DisplayName("Should throw ConstraintViolationException when creating a customer with invalid fields")
    void shouldThrowConstraintViolationExceptionWhenCreatingCustomerWithInvalidFields() {
        // Teste para nome em branco
        CreateCustomerRequest requestWithBlankName = new CreateCustomerRequest("", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "(22)998223307", "222.222.222-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithBlankName).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para email inválido
        CreateCustomerRequest requestWithInvalidEmail = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "invalid-email", "(22)998223307", "222.222.222-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidEmail).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para telefone inválido
        CreateCustomerRequest requestWithInvalidPhone = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "998223307", "222.222.222-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidPhone).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para CPF inválido
        CreateCustomerRequest requestWithInvalidCpf = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "(22)998223307", "222-22-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidCpf).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para CEP inválido
        CreateCustomerRequest requestWithInvalidCep = new CreateCustomerRequest("Name", LocalDate.of(1990, 8, 28),
                "matteus@email.com", "(22)998223307", "222.222.222-22", "2222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidCep).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });
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
    @DisplayName("Should throw NoSuchElementException when customer ID not found")
    void shouldThrowNoSuchElementExceptionWhenCustomerIdNotFound() {
        when(customerRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            customerService.customerDetailsById(uuid);
        });

        verify(customerRepository, times(1)).findById(uuid);
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
    /*
    @Test
    @DisplayName("Should update customer and save it to the repository")
    void shouldUpdateCustomerAndSaveToRepository() {
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getId(), "New Name", LocalDate.of(1989, 8, 28), "newemail@email.com",
                "(33)333333333", "000.000.000-00", newAddress.getZipcode());

        when(customerRepository.findById(request.id())).thenReturn(Optional.of(customer));
        when(addressMapper.toEntity(newAddress.getZipcode())).thenReturn(newAddress);
        when(appUtils.ageCalculator(request.birthDate())).thenReturn(34);

        Customer result = customerService.updateCustomer(request);

        verify(customerRepository, times(1)).findById(uuid);
        verify(customerRepository, times(1)).save(result);
        verify(appUtils, times(1)).ageCalculator(request.birthDate());
        verify(addressMapper, times(1)).toEntity(request.zipcode());

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
    */
    @Test
    @DisplayName("Should throw NoSuchElementException when updating a customer that does not exist")
    void shouldThrowNoSuchElementExceptionWhenUpdatingNonExistentCustomer() {
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getId(), "New Name", LocalDate.of(1989, 8, 28), "newemail@email.com",
                "(33)333333333", "000.000.000-00", newAddress.getZipcode());

        when(customerRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            customerService.updateCustomer(request);
        });

        verify(customerRepository, times(1)).findById(request.id());
        verify(customerRepository, times(0)).save(any(Customer.class));
        verify(appUtils, times(0)).ageCalculator(any(LocalDate.class));
        verify(addressMapper, times(0)).toEntity(anyString());
    }


    @Test
    @DisplayName("Should disable a customer and mark them as deleted")
    void shouldDisableCustomerAndMarkAsDeleted() {
        when(customerRepository.findById(uuid)).thenReturn(Optional.of(customer));

        customerService.disableCustomer(customer.getId());

        verify(customerRepository, times(1)).save(customer);
        assertFalse(customer.getActive());
        assertNotNull(customer.getDeletedAt());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when disabling a customer that does not exist")
    void shouldThrowNoSuchElementExceptionWhenDisablingNonExistentCustomer() {
        when(customerRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            customerService.disableCustomer(uuid);
        });

        verify(customerRepository, times(1)).findById(uuid);
        verify(customerRepository, times(0)).save(any(Customer.class));
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

    @Test
    @DisplayName("Should throw NoSuchElementException when enabling a customer that does not exist")
    void shouldThrowNoSuchElementExceptionWhenEnablingNonExistentCustomer() {
        when(customerRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            customerService.enableCustomer(uuid);
        });

        verify(customerRepository, times(1)).findById(uuid);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }
}